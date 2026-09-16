import http from 'k6/http';
import { sleep } from 'k6';
import { Trend } from 'k6/metrics';

const chatCompletionTime = new Trend('chat_completion_time');
const queueWaitTime = new Trend('queue_wait_time');
const processingTime = new Trend('processing_time');

export const options = {
    stages: [
        { duration: '15s', target: 2 },
        { duration: '15s', target: 5 },
        { duration: '20s', target: 8 },
        { duration: '10s', target: 0 },
    ],
};

export default function () {
    const message = '안녕';
    const submitUrl = `http://localhost:8080/test/chat-job?message=${encodeURIComponent(message)}`;
    const submitStart = Date.now();

    const submitRes = http.get(submitUrl);
    const jobId = submitRes.body.trim();

    let done = false;
    let job;
    let attempts = 0;

    while (!done && attempts < 100) {
        sleep(0.2);
        const pollRes = http.get(`http://localhost:8080/test/chat-job/${jobId}`);
        job = JSON.parse(pollRes.body);
        done = job.done;
        attempts++;
    }

    const elapsed = Date.now() - submitStart;
    chatCompletionTime.add(elapsed);
    queueWaitTime.add(job.queueWaitMillis);
    processingTime.add(job.processingMillis);

    console.log(`VU${__VU} job ${jobId}: queueWait=${job.queueWaitMillis}ms processing=${job.processingMillis}ms`);
}
