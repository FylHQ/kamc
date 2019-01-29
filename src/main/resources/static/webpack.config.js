const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin')

module.exports = {
    mode: "development",
    /*mode: "production",*/
    entry: {
        src: './src/index.js',
    },
    output: {
        path: __dirname + "/dist",
        filename: "bundle.js"
    },
    stats: {
        colors: true
    },
    devtool: 'source-map',
    module: {
        rules: [               
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: [
                            ['@babel/preset-env',
                                {
                                    targets: {esmodules: true},
                                    /*useBuiltIns: "usage",*/
                                    debug: false
                                }
                            ]
                        ],
                        cacheDirectory: true,
                        plugins: [
                            ["component", {
                                "libraryName": "element-ui",
                                "styleLibraryName": "theme-chalk"
                            }]
                        ]
                    }
                }
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader'
            },
            {
                test: /\.css$/,
                use: [
                    'vue-style-loader',
                    'css-loader'
                ]
            },
            {
                test: /\.scss$/,
                use: [
                  'vue-style-loader',
                  'css-loader',
                  'sass-loader'
                ]
            },
            {
                test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
                loader: 'url-loader',
                options: {
                    limit: 10000
                }
            },
            {
                test: /\.(mp4|webm|ogg|mp3|wav|flac|aac)(\?.*)?$/,
                loader: 'url-loader',
                options: {
                    limit: 10000
                }
            },
            {
                test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
                loader: 'url-loader',
                options: {
                    limit: 10000,
                    name: '[name].[ext]',
                    publicPath: '/dist',
                    useRelativePath: false
                }
            }
        ]           
    },
    plugins: [
        new VueLoaderPlugin()
    ],
};
