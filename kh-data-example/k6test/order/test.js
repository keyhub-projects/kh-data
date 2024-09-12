import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    stages: [
        { duration: '3m', target: 100*60 },
    ],
    thresholds: {
        http_req_duration: ['p(95)<200'],
    },
};

export default function () {
    let url = `http://localhost:8080/order/test`;
    let response = http.get(url);
    check(response, { 'status was 200': (r) => r.status === 200});
    sleep(1);
}

