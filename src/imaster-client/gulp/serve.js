/**
 * @author slesh
 */

var conf = require('./conf');
var path = require('path');
var clean = require('gulp-clean');
var gulp = require('gulp');
var util = require('gulp-util');
var cleanCss = require('gulp-clean-css');
var sass = require('gulp-sass');
var jshint = require('gulp-jshint');
var minify = require('gulp-minify');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var inject = require('gulp-inject');
var concat = require('gulp-concat');
var filter = require('gulp-filter');
var htmlmin = require('gulp-htmlmin');
var angularFileSort = require('gulp-angular-filesort');
var angularTemplateCache = require('gulp-angular-templatecache');
var mainBowerFiles = require('gulp-main-bower-files');
var sourcemaps = require('gulp-sourcemaps');
var ngAnnotate = require('gulp-ng-annotate');
var browserSync = require('browser-sync').create();

var grabStyles = function () {
    return gulp.src(conf.styles)
        .pipe(sourcemaps.init())
        .pipe(sass().on('error', sass.logError))
        .pipe(concat('style.min.css'))
        .pipe(cleanCss())
        .pipe(sourcemaps.write('./maps'))
        .pipe(gulp.dest(path.join(conf.target, 'app')))
        .pipe(browserSync.stream({match: '**/*.css'}));
};

var grabScripts = function () {
    return gulp.src(conf.scripts)
        .pipe(sourcemaps.init())
        .pipe(jshint())
        .pipe(jshint.reporter('jshint-stylish'))
        .pipe(angularFileSort())
        .pipe(ngAnnotate(conf.ngAnnotateOptions))
        .pipe(concat('script.js'))
        .pipe(uglify({preserveComments: 'license'}))
        .pipe(rename({extname: '.min.js'}))
        .pipe(sourcemaps.write('./maps'))
        .pipe(gulp.dest(path.join(conf.target, 'app')))
        .pipe(browserSync.stream({match: '**/*.js'}));
};

var grabTemplates = function () {
    return gulp.src(conf.templates)
        .pipe(sourcemaps.init())
        .pipe(htmlmin(conf.htmlminOptions))
        .pipe(angularTemplateCache(conf.templateCacheOptions.file, conf.templateCacheOptions.options))
        .pipe(uglify({preserveComments: 'license'}))
        .pipe(rename({extname: '.min.js'}))
        .pipe(sourcemaps.write('./maps'))
        .pipe(gulp.dest(path.join(conf.target, 'app')))
        .pipe(browserSync.stream({match: '**/*.js'}));
};

var vendor = function () {
    return gulp.src('./bower.json')
        .pipe(mainBowerFiles())
        .pipe(concat('vendor.js'))
        .pipe(gulp.dest(path.join(conf.target, 'app')));
};

gulp.task('grab-styles', grabStyles);
gulp.task('grab-scripts', grabScripts);
gulp.task('grab-templates', grabTemplates);
gulp.task('clean', function () {
    //gulp.src(conf.target, {read: false})
    //    .pipe(clean());
});

gulp.task('build', ['clean'], function () {
    gulp.src(conf.favicon).pipe(gulp.dest(conf.target));
    gulp.src(conf.images).pipe(gulp.dest(conf.target));
    return gulp.src(conf.index)
        .pipe(gulp.dest(conf.target))
        .pipe(inject(vendor(), {name: 'vendor', removeTags: true, relative: true}))
        .pipe(inject(grabStyles(), {name: 'styles', removeTags: true, relative: true}))
        .pipe(inject(grabScripts(), {name: 'scripts', removeTags: true, relative: true}))
        .pipe(inject(grabTemplates(), {name: 'templates', removeTags: true, relative: true}))
        .pipe(gulp.dest(conf.target));
});

gulp.task('serve', ['build'], function () {
    var proxyMiddleware = require('http-proxy-middleware');
    var proxy = new proxyMiddleware('/api', {target: 'http://localhost:8010'});
    browserSync.init({
        open: true,
        port: 3000,
        server: {
            baseDir: conf.target,
            middleware: [proxy]
        }
    });
    gulp.watch(conf.styles, ['grab-styles']).on('change', util.log);
    gulp.watch(conf.scripts, ['grab-scripts']).on('change', util.log);
    gulp.watch(conf.templates, ['grab-templates']).on('change', util.log);
});
