import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 10,
    duration: '3s',
};

export default function () {
    let id = "userId1";
    let url = `http://localhost:8080/order/${id}`;
    let response = http.get(url);
    check(response, { 'status was 200': (r) => r.status === 200});
    sleep(1);
}