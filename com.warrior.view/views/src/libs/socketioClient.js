import io from 'socket.io-client';
import Cookies from 'js-cookie';

let ioClient = {
    host: '127.0.0.1',
    port: 9092
};
ioClient.createNew = function() {
    let client = {};
    client.host = ioClient.host;
    client.port = ioClient.port;
    client.socket = io('http://' + ioClient.host + ':' + ioClient.port + '?token=' + Cookies.get('token'));
    client.socket.on('connect', () => {
        Cookies.set('isConnected', '1');
        console.log('success', client.socket.id);
    });
    client.socket.on('connect_error', (error) => {
        console.error('error' + error);
    });
    client.socket.on('connect_timeout', (timeout) => {
        console.log('timeout');
    });
    client.socket.on('error', (error) => {
        console.log('error' + error);
    });
    client.socket.on('disconnect', (reason) => {
        Cookies.set('isConnected', '0');
        console.log('disconnect');
    });
    client.socket.on('reconnect', (attemptnumber) => {
        console.log('reconnect: ' + attemptnumber);
    });
    client.socket.on('reconnect_error', (error) => {
        console.log('reconnect_error');
    });
    client.socket.on('reconnect_failed', () => {
        console.log('reconnect_failed');
    });
    client.send = function(event, msg, callback) {
        if (callback) {
            client.socket.emit(event, msg, (data) => callback);
        } else {
            client.socket.emit(event, msg);
        }
    };
    client.receive = function(event, callback) {
        client.socket.on(event, callback);
    }
    return client;
}
export default ioClient;