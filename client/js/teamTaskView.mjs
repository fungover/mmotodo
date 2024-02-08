
let data = [
    { col1: '1', col2: 'Create design', col3: '2023-12-13 12:00', col4: 'We need a design that we can discuss around...', col5: '2', col6: 'Design, CoOp', col7: 'Done', user: 'Liber09' },
    { col1: '2', col2: 'Create first component', col3: '2023-12-18 12:00', col4: 'Create the first component used in the design', col5: '3', col6: 'Implement', col7: 'Ready for test', user: 'Liber09' },
    { col1: '3', col2: 'Create components', col3: '2023-12-18 12:00', col4: 'Create components used in the design', col5: '3', col6: 'Implement', col7: 'Code review', user: 'User 1' },
    { col1: '4', col2: 'Apply design', col3: '2023-12-18 12:00', col4: 'Implement the design', col5: '3', col6: 'Design', col7: 'In progress', user: 'User 2' },
    { col1: '5', col2: 'Load content', col3: '2023-12-20 12:00', col4: 'Load content', col5: '3', col6: 'Design', col7: 'Blocked', user: 'Liber09' },
    { col1: '6', col2: 'Deploy', col3: '2023-12-24 12:00', col4: 'Deploy everything', col5: '3', col6: 'Deploy', col7: 'ToDo', user: '' },
];


function renderTeamTable (){
    const tbody = document.getElementById('tbody');
    const table = document.getElementById('tasksTable');
    const userList = document.getElementById('userList');
    const assigneeCol = document.getElementById('assignee')

    tbody.innerHTML = ''
data.forEach(element => {
    const tr = document.createElement('tr')
    tbody.appendChild(tr)
    const td = document.createElement('td')
    tr.appendChild(td)
    tr.innerHTML = `<td class="text-center">${element.col1}</td>
    <td>${element.col2}</td>
    <td class="text-center">${element.col3}</td>
    <td>${element.col4}</td>
    <td class="text-center">${element.col5}</td><td class="text-center">${element.col6}</td>
    <td class="text-nowrap align-middle text-center"> <span class="done">${element.col7}</span> </td>
    <td class="assigneeSelect">${element.col8}</td>
    `;
     
    





    
});
    
   
}
export default renderTeamTable
//switchView.addEventListener('click', renderTeamTable)
