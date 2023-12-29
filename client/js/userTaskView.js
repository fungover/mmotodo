document.addEventListener('DOMContentLoaded', function() {
    const data = [
        { col1: '1', col2: 'Create design', col3: '2023-12-13 12:00', col4: 'We need a design that we can discuss around...', col5: '2', col6: 'Design, CoOp', col7: 'Done', user: 'Liber09' },
        { col1: '2', col2: 'Create first component', col3: '2023-12-18 12:00', col4: 'Create the first component used in the design', col5: '3', col6: 'Implement', col7: 'Ready for test', user: 'Liber09' },
        { col1: '3', col2: 'Create components', col3: '2023-12-18 12:00', col4: 'Create components used in the design', col5: '3', col6: 'Implement', col7: 'Code review', user: 'User 1' },
        { col1: '4', col2: 'Apply design', col3: '2023-12-18 12:00', col4: 'Implement the design', col5: '3', col6: 'Design', col7: 'In progress', user: 'User 2' },
        { col1: '5', col2: 'Load content', col3: '2023-12-20 12:00', col4: 'Load content', col5: '3', col6: 'Design', col7: 'Blocked', user: 'Liber09' },
        { col1: '6', col2: 'Deploy', col3: '2023-12-24 12:00', col4: 'Deploy everything', col5: '3', col6: 'Deploy', col7: 'ToDo', user: '' },
    ];
    var users = ['Liber09', 'User 1', 'User 2', 'User 3'];

    const tbody = document.querySelector('tbody');
    const table = document.getElementById('tasksTable');
    const userList = document.getElementById('userList');
    let selectedUser = null;

    function populateUserList() {
        // Clear existing user list
        userList.innerHTML = '';

        // Add "All Users" option
        var allUsersOption = document.createElement('li');
        var allUsersAnchor = document.createElement('a');
        allUsersAnchor.id = 'allUsersOption';
        allUsersAnchor.classList.add('dropdown-item');
        allUsersAnchor.textContent = 'All Users';
        allUsersAnchor.addEventListener('click', function(event) {
            event.preventDefault(); // Prevent default navigation behavior
            selectUser(null);
        });
        allUsersOption.appendChild(allUsersAnchor);
        userList.appendChild(allUsersOption);

        // Add "Unassigned" option
        var unassignedOption = document.createElement('li');
        var unassignedAnchor = document.createElement('a');
        unassignedAnchor.id = 'unassignedOption';
        unassignedAnchor.classList.add('dropdown-item');
        unassignedAnchor.textContent = 'Unassigned';
        unassignedAnchor.addEventListener('click', function(event) {
            event.preventDefault(); // Prevent default navigation behavior
            selectUser('');
        });
        unassignedOption.appendChild(unassignedAnchor);
        userList.appendChild(unassignedOption);

        // Add user-specific options
        users.forEach(function(user) {
            var listItem = document.createElement('li');
            var anchor = document.createElement('a');
            anchor.classList.add('dropdown-item');
            anchor.href = '#';
            anchor.textContent = user;
            anchor.addEventListener('click', function(event) {
                event.preventDefault(); // Prevent default navigation behavior
                selectUser(user);
            });
            listItem.appendChild(anchor);
            userList.appendChild(listItem);
        });
    }

    function populateTable(data, selectedUser) {
        // Clear existing rows
        tbody.innerHTML = '';

        // Filter tasks based on the selected user
        const userTasks = selectedUser !== null
            ? (selectedUser === '' ? data.filter(task => !task.user) : data.filter(task => task.user === selectedUser))
            : data;

        if (userTasks.length === 0) {
            // If no tasks are assigned to the user, create a single row with a message
            const noTasksRow = document.createElement('tr');
            const noTasksCell = document.createElement('td');
            noTasksCell.colSpan = 7; // Set colSpan to match the number of columns
            noTasksCell.textContent = selectedUser !== null
                ? (selectedUser === '' ? 'No unassigned tasks' : `No tasks assigned for user '${selectedUser}'`)
                : 'No tasks available';
            noTasksRow.appendChild(noTasksCell);
            tbody.appendChild(noTasksRow);
        } else {
            // Get the list of columns to display (excluding the 'username' column)
            const columnsToDisplay = Object.keys(userTasks[0]).filter(col => col !== 'user');

            // Populate the table with tasks
            userTasks.forEach(function(rowData) {
                var row = document.createElement('tr');

                columnsToDisplay.forEach(function(key) {
                    var cell = document.createElement('td');
                    var textContentSpan = document.createElement('span');

                    if (key === 'col7') {
                        textContentSpan.textContent = rowData[key];
                        setTaskStatusStyle(textContentSpan);
                        cell.appendChild(textContentSpan);
                    } else {
                        cell.textContent = rowData[key];
                    }

                    if (key !== 'col2' && key !== 'col4') {
                        cell.classList.add('text-center');
                    }

                    row.appendChild(cell);
                });

                tbody.appendChild(row);
            });
        }
    }

    function setTaskStatusStyle(span) {
        if (span.textContent === 'In progress') {
            span.classList.add('inProgress');
        } else if (span.textContent === 'ToDo') {
            span.classList.add('toDo');
        } else if (span.textContent === 'Done') {
            span.classList.add('done');
        } else if (span.textContent === 'Ready for test') {
            span.classList.add('readyForTest');
        } else if (span.textContent === 'Blocked') {
            span.classList.add('blocked');
        } else if (span.textContent === 'Code review') {
            span.classList.add('codeReview');
        }
    }

    function sortTable(columnIndex) {
        let isAscending = true;

        let thClicked = table.querySelectorAll('th')[columnIndex];
        const key = thClicked.getAttribute('data-key');

        table.querySelectorAll('th').forEach(function (th, index) {
            if (index !== columnIndex) {
                th.classList.remove('ascending', 'descending');
            }
        });

        if (thClicked.classList.contains('ascending')) {
            isAscending = false;
        }

        data.sort(function (a, b) {
            let valueA = a[key] !== undefined ? a[key].toUpperCase() : '';
            let valueB = b[key] !== undefined ? b[key].toUpperCase() : '';

            if (isAscending) {
                return valueA.localeCompare(valueB);
            } else {
                return valueB.localeCompare(valueA);
            }
        });

        thClicked.classList.toggle('ascending', isAscending);
        thClicked.classList.toggle('descending', !isAscending);

        populateTable(data, selectedUser);
    }


    // Function to filter tasks by selected user
    function selectUser(user) {
        selectedUser = user;
        const dropdownButton = document.getElementById('dropdownButton');
        dropdownButton.textContent = user !== null ? (user === '' ? 'Unassigned' : user) : 'All Users';

        // Filter and populate the table
        const filteredData = data.filter(function (task) {
            return selectedUser !== null ? (selectedUser === '' ? !task.user : task.user === selectedUser) : true;
        });

        populateTable(filteredData, selectedUser);
    }

    // Initial table population with all tasks
    populateTable(data);

    // Populate user list
    populateUserList();
    selectUser(null);

    // Add click event listeners to table headers for sorting
    table.querySelectorAll('th').forEach(function(th, index) {
        th.addEventListener('click', function() {
            sortTable(index);
        });
    });
});
