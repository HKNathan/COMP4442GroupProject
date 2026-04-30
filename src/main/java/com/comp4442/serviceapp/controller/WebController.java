package com.comp4442.serviceapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Distributed Task Computing Service</title>\n" +
                "    <style>\n" +
                "        body { font-family: Arial, sans-serif; max-width: 700px; margin: 40px auto; padding: 20px; background: #f5f5f5; }\n" +
                "        .card { background: white; border-radius: 8px; padding: 24px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }\n" +
                "        input, button { font-size: 16px; padding: 8px 12px; }\n" +
                "        input { width: 100%; margin: 8px 0 16px; border: 1px solid #ccc; border-radius: 4px; }\n" +
                "        button { background: #0078d4; color: white; border: none; border-radius: 4px; cursor: pointer; margin-right: 8px; }\n" +
                "        button:hover { background: #005a9e; }\n" +
                "        pre { background: #eee; padding: 12px; border-radius: 4px; overflow-x: auto; }\n" +
                "        .status { font-weight: bold; }\n" +
                "        hr { margin: 20px 0; }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"card\">\n" +
                "    <h2>Distributed Task Computing Service</h2>\n" +
                "    <p>Enter a mathematical expression (supports +, -, *, /, (, ), sqrt, sin, cos, etc.)</p>\n" +
                "    <label for=\"expr\">Expression:</label>\n" +
                "    <input type=\"text\" id=\"expr\" placeholder=\"Example: (3+5)*2  or  sqrt(25)+3\" value=\"(3+5)*2\">\n" +
                "    <button onclick=\"submitTask()\">Submit Task</button>\n" +
                "    <button onclick=\"clearResult()\">Clear</button>\n" +
                "    <hr>\n" +
                "    <h3>Task Result</h3>\n" +
                "    <div id=\"taskInfo\"></div>\n" +
                "    <pre id=\"jsonResult\">Waiting for submission...</pre>\n" +
                "    <p><small>Note: Tasks are processed asynchronously. The page will poll until completion.</small></p>\n" +
                "</div>\n" +
                "<script>\n" +
                "    function submitTask() {\n" +
                "        const expr = document.getElementById('expr').value.trim();\n" +
                "        if (!expr) { alert('Please enter an expression.'); return; }\n" +
                "        document.getElementById('jsonResult').innerText = 'Submitting...';\n" +
                "        document.getElementById('taskInfo').innerHTML = '';\n" +
                "        fetch('/api/tasks', {\n" +
                "            method: 'POST',\n" +
                "            headers: { 'Content-Type': 'application/json' },\n" +
                "            body: JSON.stringify({ expression: expr })\n" +
                "        })\n" +
                "        .then(response => response.json())\n" +
                "        .then(task => { displayTask(task); pollTask(task.id); })\n" +
                "        .catch(error => { document.getElementById('jsonResult').innerText = 'Error: ' + error; });\n" +
                "    }\n" +
                "    function pollTask(taskId, attempt = 0) {\n" +
                "        if (attempt > 30) return;\n" +
                "        setTimeout(() => {\n" +
                "            fetch('/api/tasks/' + taskId)\n" +
                "            .then(res => res.json())\n" +
                "            .then(task => { displayTask(task); if (task.status === 'PENDING' || task.status === 'PROCESSING') pollTask(taskId, attempt + 1); })\n" +
                "            .catch(err => console.log('Polling error', err));\n" +
                "        }, 1000);\n" +
                "    }\n" +
                "    function displayTask(task) {\n" +
                "        const info = `<p><strong>Task ID:</strong> ${task.id}</p>` +\n" +
                "                      `<p><strong>Expression:</strong> ${task.expression}</p>` +\n" +
                "                      `<p><strong>Status:</strong> <span class=\"status\">${task.status}</span></p>` +\n" +
                "                      `<p><strong>Result:</strong> ${task.result !== null ? task.result : 'not ready'}</p>` +\n" +
                "                      (task.errorMessage ? `<p><strong>Error:</strong> ${task.errorMessage}</p>` : '');\n" +
                "        document.getElementById('taskInfo').innerHTML = info;\n" +
                "        document.getElementById('jsonResult').innerText = JSON.stringify(task, null, 2);\n" +
                "    }\n" +
                "    function clearResult() { document.getElementById('taskInfo').innerHTML = ''; document.getElementById('jsonResult').innerText = 'Waiting for submission...'; document.getElementById('expr').value = ''; }\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
    }
}