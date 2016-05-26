/**
 * @author yauheni.putsykovich
 */

var path = require('path');

var conf = {
    module: 'imaster',
    src: './src',
    temp: './.tmp',
    target: './build'
};

conf.app = path.join(conf.src, '/app');

conf.index = path.join(conf.src, '/index.html');
conf.favicon = path.join(conf.src, 'favicon.ico');

conf.resources  = path.join(conf.src, '/resources');
conf.dependencies = path.join(conf.src, '/dependencies');

conf.scripts = path.join(conf.app, '/**/*.js');
conf.templates = path.join(conf.app, '/**/*.html');
conf.styles = [path.join(conf.resources, '/**/*.scss'), path.join(conf.app, '/**/*.scss')];
conf.images = path.join(conf.resources, '/**/*[.png, .gif]');

conf.templateCacheOptions = {
    file: 'templates.js',
    options: {
        module: conf.module,
        standAlone: false,
        base: path.resolve(conf.src)
    }
};

conf.htmlminOptions = {
    collapseWhitespace: true
};

conf.ngAnnotateOptions = {
    remove: true,
    add: true,
    single_quotes: true
};

module.exports = conf;