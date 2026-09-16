import http from 'k6/http';
import { sleep } from 'k6';
import { Trend } from 'k6/metrics';

const chatCompletionTime = new Trend('chat_completion_time');

export const options = {
    vus: 5,
    iterations: 5,
};

export default function () {
    const message = '안녕';
    const submitUrl = `http://localhost:8080/test/chat-job?message=${encodeURIComponent(message)}`;
    const submitStart = Date.now();

    const submitRes = http.get(submitUrl);
    const jobId = submitRes.body.trim();

    let done = false;
    let attempts = 0;

    while (!done && attempts < 100) {
        sleep(0.2);
        const pollRes = http.get(`http://localhost:8080/test/chat-job/${jobId}`);
        const job = JSON.parse(pollRes.body);
        done = job.done;
        attempts++;
    }

    const elapsed = Date.now() - submitStart;
    chatCompletionTime.add(elapsed);
}
