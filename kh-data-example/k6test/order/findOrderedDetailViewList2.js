import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '30s', target: 100 },
        { duration: '30s', target: 200 },
        { duration: '30s', target: 100 },
        { duration: '30s', target: 200 },
        { duration: '30s', target: 100 },
    ],
    thresholds: {
        http_req_duration: ['p(95)<200'],
    },
};

export default function () {
    let id = "userId1";
    let url = `http://localhost:8080/order/detail/${id}`;
    let response = http.get(url);
    check(response, { 'status was 200': (r) => r.status === 200});
    sleep(1);
}

