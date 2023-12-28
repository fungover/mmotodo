document.addEventListener('DOMContentLoaded', function() {
    var data = [
        { col1: '1', col2: 'Create design', col3: '2023-12-13 12:00', col4: 'We need a design that we can discuss around...', col5: '2', col6: 'Design, CoOp', col7: 'Done' },
        { col1: '2', col2: 'Create first component', col3: '2023-12-18 12:00', col4: 'Create the first component used in the design', col5: '3', col6: 'Implement', col7: 'Ready for test' },
        { col1: '3', col2: 'Create components', col3: '2023-12-18 12:00', col4: 'Create components used in the design', col5: '3', col6: 'Implement', col7: 'Code review' },
        { col1: '4', col2: 'Apply design', col3: '2023-12-18 12:00', col4: 'Implement the design', col5: '3', col6: 'Design', col7: 'In progress' },
        { col1: '5', col2: 'Load content', col3: '2023-12-20 12:00', col4: 'Load content', col5: '3', col6: 'Design', col7: 'Blocked' },
        { col1: '6', col2: 'Deploy', col3: '2023-12-24 12:00', col4: 'Deploy everything', col5: '3', col6: 'Deploy', col7: 'ToDo' },
    ];

    var tbody = document.querySelector('tbody');
    var table = document.getElementById('tasksTable');

    function populateTable(data) {
        // Clear existing rows
        tbody.innerHTML = '';

        // Populate the table
        data.forEach(function(rowData) {
            var row = document.createElement('tr');

            for (var key in rowData) {
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
            }

            tbody.appendChild(row);
        });
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
        var isAscending = true;

        var thClicked = table.querySelectorAll('th')[columnIndex];
        var key = thClicked.getAttribute('data-key');

        table.querySelectorAll('th').forEach(function(th, index) {
            if (index !== columnIndex) {
                th.classList.remove('ascending', 'descending');
            }
        });

        if (thClicked.classList.contains('ascending')) {
            isAscending = false;
        }

        data.sort(function(a, b) {
            var valueA = a[key].toUpperCase();
            var valueB = b[key].toUpperCase();

            if (isAscending) {
                return valueA.localeCompare(valueB);
            } else {
                return valueB.localeCompare(valueA);
            }
        });

        thClicked.classList.toggle('ascending', isAscending);
        thClicked.classList.toggle('descending', !isAscending);

        populateTable(data);
    }

    sortTable(0);

    // Add click event listeners to table headers for sorting
    table.querySelectorAll('th').forEach(function(th, index) {
        th.addEventListener('click', function() {
            sortTable(index);
        });
    });
});