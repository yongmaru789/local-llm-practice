import http from 'k6/http';

const systemPrompt = open('../src/main/resources/prompts/style-change-prompt.txt');

export const options = {
    vus: 5,
    iterations: 5,
};

const messages = [
    '배경색을 빨간색으로 바꿔줘',
    '텍스트를 오른쪽으로 옮겨줘',
    '배경색을 파란색으로 바꿔줘',
    '텍스트 크기를 좀 더 키워줘',
    '배경색을 초록색으로 바꿔줘',
];

export default function () {
    const url = 'http://localhost:11434/api/chat';
    const message = messages[Math.floor(Math.random() * messages.length)];

    const payload = JSON.stringify({
        model: 'qwen3.5:2b',
        messages: [
            { role: 'system', content: systemPrompt },
            { role: 'user', content: message },
        ],
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
