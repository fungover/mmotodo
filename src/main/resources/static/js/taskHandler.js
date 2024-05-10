function getAllTasks() {
    fetch("/graphql", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            query: `
                {
                    allTasks {
                        id
                        description
                        title
                    }
                }
            `
        })
    })
        .then(response => response.json())
        .then(data => {
            // Extract tasks from the response
            const tasks = data.data.allTasks;
            console.log(tasks)

            // Render tasks in the HTML
            const taskList = document.getElementById('taskList');
            taskList.innerHTML = ""
            tasks.forEach(task => {
                const li = document.createElement('li');
                li.textContent = `ID: ${task.id}, Title: ${task.title}, Description: ${task.description}`;
                taskList.appendChild(li);
            });
        })
        .catch(error => {
            console.error('Error fetching tasks:', error);
        });
}

getAllTasks();

// Function to add a new task
function addTask() {
    const title = document.getElementById('titleInput').value;
    const description = document.getElementById('descriptionInput').value;

    fetch("/graphql", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            query: `
                mutation ADD {
                    addTask(
                        task: {description: "${description}", title: "${title}"}
                    ) {
                        id
                        description
                        title
                    }
                }
            `
        })
    })
        .then(response => response.json())
        .then(data => {
            // Handle the response here, maybe update the UI with the new task
            console.log('Task added successfully:', data);
            // After adding the task, refresh the list of tasks
            getAllTasks();
        })
        .catch(error => {
            console.error('Error adding task:', error);
        });
}
