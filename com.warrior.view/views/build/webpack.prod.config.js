const webpack = require('webpack')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const ExtractTextPlugin = require('extract-text-webpack-plugin')
const merge = require('webpack-merge')
const webpackBaseConfig = require('./webpack.base.config.js')
const fs = require('fs')
const CleanWebpackPlugin = require('clean-webpack-plugin')

fs.open('./src/config/env.js', 'w', function (err, fd) {
  const buf = 'export default "production";'
  fs.write(fd, buf, 0, buf.length, 0, function (err, written, buffer) {})
})

module.exports = merge(webpackBaseConfig, {
  output: {
    publicPath: 'http://120.78.220.187:8080/admin/dist/',
    filename: '[name].[hash].js',
    chunkFilename: '[name].[hash].chunk.js'
  },
  plugins: [
    new CleanWebpackPlugin(
      [
        'dist/*.chunk.js',
        'dist/*.eot',
        'dist/*.woff',
        'dist/*.svg',
        'dist/*.jpg',
        'dist/*.ttf',
        'dist/main.*',
        'dist/vendors.*'
      ], {
        root: 'D:/develop/gitReps/ManageSystem/com.warrior.view/views',
        verbose: true
      }
    ),
    new ExtractTextPlugin({
      filename: '[name].[hash].css',
      allChunks: true
    }),
    new webpack.optimize.CommonsChunkPlugin({
      name: 'vendors',
      filename: 'vendors.[hash].js'
    }),
    new webpack.DefinePlugin({
      'process.env': {
        NODE_ENV: '"production"'
      }
    }),
    new webpack.optimize.UglifyJsPlugin({
      compress: {
        warnings: false
      }
    }),
    new HtmlWebpackPlugin({
      filename: '../index.html',
      template: './src/template/index.ejs',
      inject: false
    })
  ]
})