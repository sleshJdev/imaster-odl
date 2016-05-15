/**
 * @author yauheni.putsykovich
 */

function initConfig(){
    var path = require('path');
    var src = './src';
    var app = './src/app';
    var resources = './src/resources';
    var index = './src/index.html';
    return {
        module: "imaster",
        index: index,
        html: [index, path.join(app, '/**/*.html')],
        scripts: [path.join(app, 'index.js'), path.join(app, '/**/*.js')],
        styles: [path.join(app, '/**/*.scss'), path.join(resources, '/**/*.scss')],
        images: [path.join(resources, '/images/**/*')],
        temp: "./.tmp",
        target: "./build",
        dependencies: "./src/dependencies"
    }
}

module.exports = initConfig();