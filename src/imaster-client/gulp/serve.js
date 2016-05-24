/**
 * @author slesh
 */

var conf = require('./conf');
var path = require('path');
var del = require('del');
var gulp = require('gulp');
var util = require('gulp-util');
var cleanCss = require('gulp-clean-css');
var sass = require('gulp-sass');
var jshint = require('gulp-jshint');
var uglify = require('gulp-uglify');
var inject = require('gulp-inject');
var concat = require('gulp-concat');
var angularFileSort = require('gulp-angular-filesort');
var angularTemplateCache = require('gulp-angular-templatecache');
var mainBowerFiles = require('gulp-main-bower-files');
var browserSync = require('browser-sync').create();
var sourcemaps = require('gulp-sourcemaps');

var paths = {
    styles: [path.join(conf.resources, '/**/*.scss'), path.join(conf.app, '/**/*.scss')],
    scripts: path.join(conf.app, '/**/*.js'),
    templates: path.join(conf.app, '/**/*.html'),
    images: path.join(conf.resources, '/**/*[.png, .gif]')
};

var grabStyles = function () {
    return gulp.src(paths.styles)
        .pipe(sourcemaps.init())
        .pipe(sass().on('error', sass.logError))
        .pipe(concat('main.css'))
        .pipe(cleanCss())
        .pipe(sourcemaps.write({includeContent: true}))
        .pipe(gulp.dest(conf.target))
        .pipe(browserSync.stream({match: '**/*.css'}));
};

var grabScripts = function () {
    return gulp.src(paths.scripts)
        .pipe(angularFileSort())
        .pipe(sourcemaps.init())
        .pipe(concat('script.js'))
        .pipe(uglify())
        .pipe(jshint())
        .pipe(sourcemaps.write({includeContent: true}))
        .pipe(gulp.dest(conf.target))
        .pipe(browserSync.stream({match: '**/*.js'}));
};

var grabTemplates = function () {
    var templateCacheOptions = {
        module: conf.module,
        standAlone: false,
        base: path.resolve(conf.src)
    };

    return gulp.src(paths.templates)
        .pipe(angularTemplateCache('templates.js', templateCacheOptions))
        .pipe(gulp.dest(conf.target))
        .pipe(browserSync.stream({match: '**/*.js'}));
};

var vendor = function () {
    return gulp.src('./bower.json')
        .pipe(mainBowerFiles())
        .pipe(concat('vendor.js'))
        .pipe(gulp.dest(conf.target));
};

gulp.task('clean', function () { del(conf.target); });
gulp.task('grab-styles', grabStyles);
gulp.task('grab-scripts', grabScripts);
gulp.task('grab-templates', grabTemplates);

gulp.task('build', function () {
    gulp.src(conf.favicon).pipe(gulp.dest(conf.target));
    gulp.src(paths.images).pipe(gulp.dest(conf.target));
    return gulp.src(conf.index)
        .pipe(gulp.dest(conf.target))
        .pipe(inject(vendor(), {name: 'vendor', relative: true}))
        .pipe(inject(grabStyles(), {name: 'styles', relative: true}))
        .pipe(inject(grabScripts(), {name: 'scripts', relative: true}))
        .pipe(inject(grabTemplates(), {name: 'templates', relative: true}))
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
    gulp.watch(paths.styles, ['grab-styles']).on('change', util.log);
    gulp.watch(paths.scripts, ['grab-scripts']).on('change', util.log);
    gulp.watch(paths.templates, ['grab-templates']).on('change', util.log);
});


