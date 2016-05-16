/**
 * @author yauheni.putsykovich
 */

function initConfig(){
    var path = require('path');
    var src = './src';
    var app = './src/app';
    var dependencies = './src/dependencies';
    var resources = './src/resources';
    var index = './src/index.html';
    var shim = {
        shim: {
            angular: {
                path: path.join(dependencies, 'angular/angular.js'),
                exports: 'angular'
            },
            'angular-ui-router': {
                path: path.join(dependencies, 'angular-ui-router/release/angular-ui-router.js'),
                exports: 'uiRouter',
                depends: {
                    angular: 'angular'
                }
            }
        }
    };
    return {
        module: 'imaster',
        index: index,
        html: ['!' + index, path.join(app, '/**/*.html')],
        scripts: [path.join(app, 'index.js'), path.join(app, '/**/*.js')],
        styles: [path.join(app, '/**/*.scss'), path.join(resources, '/**/*.scss')],
        images: [path.join(src, 'favicon.ico'), path.join(resources, '/images/**/*')],
        temp: './.tmp',
        target: './build',
        dependencies: dependencies,
        shim: shim
    }
}

module.exports = initConfig();