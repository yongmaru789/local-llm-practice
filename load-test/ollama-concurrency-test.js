import http from 'k6/http';

export const options = {
    vus: 5,
    iterations: 5,
};

export default function () {
    const url = 'http://localhost:11434/api/chat';
    const payload = JSON.stringify({
        model: 'qwen3.5:2b',
        messages: [{ role: 'user', content: '안녕' }],
        stream: false,
        think: false,
        keep_alive: '30m',
    });

    const params = {
        headers: {
            'Content-Type': 'application/json; charset=utf-8',
        },
    };

    http.post(url, payload, params);
}
