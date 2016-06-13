//
// If absolute URL from the remote server is provided, configure the CORS
// header on that server.
//


//
// Disable workers to avoid yet another cross-origin issue (workers need
// the URL of the script to be loaded, and dynamically loading a cross-origin
// script does not work).
//
// PDFJS.disableWorker = true;

//
// In cases when the pdf.worker.js is located at the different folder than the
// pdf.js's one, or the pdf.js is executed via eval(), the workerSrc property
// shall be specified.
//
// PDFJS.workerSrc = '../../build/pdf.worker.js';

'use strict';

/**
 *
 * @param {string} id
 * @param {string} url
 * @param {function} callback
 * @constructor
 */
var PdfView = function (id, url, callback) {
    this.id = id;
    this.url = url;
    this.pdfDoc = null;
    this.pagePos = 1;
    this.pageRendering = false;
    this.pageNumPending = null;
    this.scale = 1.5;
    this.callback = callback;
};

/**
 * Initialize dom of pdf viewer.
 */
PdfView.prototype.init = function () {
    console.log(this.id);
    this.canvas = document.getElementById(this.id);
    console.log(this.canvas);
    this.ctx = this.canvas.getContext('2d');
    /**
     * Asynchronously downloads PDF.
     */
    var that = this;
    PDFJS.getDocument(this.url).then(function (pdfDoc_) {
        that.pdfDoc = pdfDoc_;
        document.getElementById(that.id).textContent = that.pdfDoc.numPages;

        // Initial/first page rendering
        that.renderPage(that.pagePos);
    });
};

/**
 * Get page info from document, resize canvas accordingly, and render page.
 * @param {number} num Page number.
 */
PdfView.prototype.renderPage = function (num) {
    this.pageRendering = true;
    // Using promise to fetch the page
    var that = this;
    this.pdfDoc.getPage(num).then(function (page) {
        var viewport = page.getViewport(that.scale);
        that.canvas.height = viewport.height;
        that.canvas.width = viewport.width;

        // Render PDF page into canvas context
        var renderContext = {
            canvasContext: that.ctx,
            viewport: viewport
        };
        var renderTask = page.render(renderContext);

        // Wait for rendering to finish
        renderTask.promise.then(function () {
            that.pageRendering = false;
            if (that.pageNumPending !== null) {
                // New page rendering is pending
                that.renderPage(that.pageNumPending);
                that.pageNumPending = null;
            }
        });
    });

    this.callback(this.pagePos, this.pdfDoc.numPages);
    // Update page counters
    //document.getElementById('pagePos').textContent = this.pagePos.toString();
};


/**
 * If another page rendering in progress, waits until the rendering is
 * finished. Otherwise, executes rendering immediately.
 *
 * @param {number} num page number.
 */
PdfView.prototype.queueRenderPage = function (num) {
    if (this.pageRendering) {
        this.pageNumPending = num;
    } else {
        this.renderPage(num);
    }
};

/**
 * Turn page
 * @param {number} inc increment
 */
PdfView.prototype.increment = function (inc) {
    var pos = this.pagePos + inc;
    var count = this.pdfDoc.numPages;

    // guard out of range.
    if (pos < 1 || count < pos) {
        return;
    }

    this.pagePos = pos;
    this.queueRenderPage(this.pagePos);
};