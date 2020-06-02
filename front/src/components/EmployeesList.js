import React from "react";
import {Button, Card, Col, Form, FormControl, InputGroup, Table} from "react-bootstrap";
import axios from 'axios';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faFastBackward,
    faFastForward, faSave, faSearch,
    faStepBackward,
    faStepForward, faTimes
} from '@fortawesome/free-solid-svg-icons'
// import DialogTitle from "@material-ui/core/DialogTitle";
// import DialogContent from "@material-ui/core/DialogContent";
// import TextField from "@material-ui/core/TextField";
// import DialogActions from "@material-ui/core/DialogActions";
// import Dialog from "@material-ui/core/Dialog";
import MyToast from "./MyToast";
import {faEdit} from "@fortawesome/free-regular-svg-icons";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import {Link} from "react-router-dom";
import DatePicker from "react-datepicker";
import moment from "moment";

export default class AnimalsList extends React.Component {

    constructor(props) {
        super(props);
        this.state = this.initialState;
        this.state = {
            employees: [],
            currentPage: 1,
            employeesPerPage: 10,
            open: false,
            employeeId: 0,
            show: false,
            message: '',
            countEmployees: 0,
            search: '',
            categories: [],
            orderByDuration: false,
            orderByAge: false,
            orderByGender: false,
            orderBySalary: false,
            animalSearch: false
        };
        this.searchChange = this.searchChange.bind(this);
        this.submitForm = this.submitForm.bind(this);
    }

    initialState = {
        kindAnimal: '',
        beginDate: null,
        endDate: null
    };

    componentDidMount() {
        this.findAllEmployee(this.state.currentPage);
        this.setCountEmployees();
        this.findAllCategories();
    }

    findAllCategories = () => {
        axios.get("http://localhost:8080/type/employeeCategory")
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    categories: [{value: '', display: 'Выберите должность'}]
                        .concat(data.map(category => {
                            return {value: category, display: category}
                        }))
                });
            });
    };

    setCountEmployees() {
        axios.get("http://localhost:8080/employees/count")
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    countEmployees: data
                });
            });
    }

    findAllEmployee(currentPage) {
        currentPage -= 1;
        axios.get("http://localhost:8080/employees/showAll?page=" + currentPage + "&size=" + this.state.employeesPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    employees: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1
                });
            });
    }

    searchChange = event => {
        this.setState({
            [event.target.name] : event.target.value
        });
    };

    cancelSearch = () => {
        this.setState({"search" : ''});
        this.findAllEmployee(this.state.currentPage);
    };

    cancelAnimalSearch = () => {
        this.setState({"animalSearch" : false});
        this.findAllEmployee(this.state.currentPage);
    };

    searchData = (currentPage) => {
        currentPage -= 1;
        if(isNaN(currentPage)) {
            currentPage = 0;
        }
        if(currentPage > this.state.totalPages) {
            currentPage = this.state.totalPages;
        }
        axios.get("http://localhost:8080/employees/byCategory?category="+this.state.search+"&page="+currentPage+"&size="+this.state.employeesPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    employees: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1
                });
            });
    };

    changePage = event => {
        let targetPage = parseInt(event.target.value);
        if (isNaN(targetPage)) {
            targetPage = this.state.currentPage;
        }
        if (targetPage > this.state.totalPages) {
            targetPage = this.state.totalPages;
        }
        if(this.state.search) {
            this.searchData(targetPage);
        } else if(this.state.orderByDuration){
            this.orderByDurationF(targetPage)
        } else if(this.state.orderByAge){
            this.orderByAgeF(targetPage)
        } else if(this.state.orderByGender){
            this.orderByGenderF(targetPage)
        } else if(this.state.orderBySalary){
            this.orderBySalaryF(targetPage)
        } else {
            this.findAllEmployee(targetPage);
        }
        this.setState({
            [event.target.name]: targetPage
        });
    };

    firstPage = () => {
        let firstPage = 1;
        if (this.state.currentPage > firstPage) {
            if(this.state.search) {
                this.searchData(firstPage);
            } else if(this.state.orderByDuration){
                this.orderByDurationF(firstPage)
            } else if(this.state.orderByAge){
                this.orderByAgeF(firstPage)
            } else if(this.state.orderByGender){
                this.orderByGenderF(firstPage)
            } else if(this.state.orderBySalary){
                this.orderBySalaryF(firstPage)
            } else {
                this.findAllEmployee(firstPage);
            }
        }
    };

    prevPage = () => {
        let prevPage = 1;
        if (this.state.currentPage > prevPage) {
            if(this.state.search) {
                this.searchData(this.state.currentPage - prevPage);
            } else if(this.state.orderByDuration){
                this.orderByDurationF(this.state.currentPage - prevPage)
            } else if(this.state.orderByAge){
                this.orderByAgeF(this.state.currentPage - prevPage)
            } else if(this.state.orderByGender){
                this.orderByGenderF(this.state.currentPage - prevPage)
            } else if(this.state.orderBySalary){
                this.orderBySalaryF(this.state.currentPage - prevPage)
            } else {
                this.findAllEmployee(this.state.currentPage - prevPage);
            }
        }
    };

    lastPage = () => {
        let condition = Math.ceil(this.state.totalElements / this.state.employeesPerPage);
        if (this.state.currentPage < condition) {
            if(this.state.search) {
                this.searchData(condition);
            } else if(this.state.orderByDuration){
                this.orderByDurationF(condition)
            } else if(this.state.orderByAge){
                this.orderByAgeF(condition)
            } else if(this.state.orderByGender){
                this.orderByGenderF(condition)
            } else if(this.state.orderBySalary){
                this.orderBySalaryF(condition)
            } else {
                this.findAllEmployee(condition);
            }
        }
    };

    nextPage = () => {
        if (this.state.currentPage < Math.ceil(this.state.totalElements / this.state.employeesPerPage)) {
            if(this.state.search) {
                this.searchData(this.state.currentPage + 1);
            } else if(this.state.orderByDuration){
                this.orderByDurationF(this.state.currentPage + 1)
            } else if(this.state.orderByAge){
                this.orderByAgeF(this.state.currentPage + 1)
            } else if(this.state.orderByGender){
                this.orderByGenderF(this.state.currentPage + 1)
            } else if(this.state.orderBySalary){
                this.orderBySalaryF(this.state.currentPage + 1)
            } else {
                this.findAllEmployee(this.state.currentPage + 1);
            }
        }
    };

    deleteEmployee = (employeeId) => {
        axios.delete("http://localhost:8080/employees/deleteEmployeeById?id=" + employeeId)
            .then(() => {
                this.setState({
                    "show": true,
                    "message": 'Удалено успешно'
                });
                if(this.state.search) {
                    this.searchData(this.state.currentPage);
                } else if(this.state.orderByDuration){
                    this.orderByDurationF(this.state.currentPage)
                } else if(this.state.orderByAge){
                    this.orderByAgeF(this.state.currentPage)
                } else if(this.state.orderByGender){
                    this.orderByGenderF(this.state.currentPage)
                } else if(this.state.orderBySalary){
                    this.orderBySalaryF(this.state.currentPage)
                } else {
                    this.findAllEmployee(this.state.currentPage);
                }
                setTimeout(() => this.setState({"show": false}), 3000);
            })
    };

    handleClickOpen = (employeeId) => {
        this.setState({
            "employeeId": employeeId,
            "open": true
        });
    };

    handleClose = () => {
        this.setState({
            "employeeId": 0,
            "open": false
        });
    };

    handleChange = date => {
        this.setState({
            "beginDate": date
        });
    };

    handleEndChange = date => {
        this.setState({
            "endDate": date
        });
    };

    submitForm = event => {
        event.preventDefault();

        const info = {
            page: this.state.currentPage - 1,
            size: this.state.employeesPerPage,
            kind: this.state.kindAnimal,
            begin: this.state.beginDate === null ? null : moment(this.state.beginDate).format('YYYY-MM-DD'),
            end: this.state.endDate === null ? null : moment(this.state.endDate).format('YYYY-MM-DD')
        }
        console.log("s ", info);
        axios.put("http://localhost:8080/employees/responsibleAnimalQuery", info)
            .then(response => response.data)
            .then((data) => {
                console.log("s ", data);
                this.setState({
                    employees: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1,
                    orderByDuration: false,
                    orderByAge: false,
                    orderByGender: false,
                    orderBySalary: false,
                    search: '',
                    animalSearch: true
                })
            })
            .catch(error => {
                this.setState({
                    "show": true,
                    "message": error.response.status === 400 ? error.response.data.errors[0].defaultMessage : error.response.data
                });
            });

        setTimeout(() => this.setState({"show": false}), 3000);
        this.setState(this.initialState);
    };

    orderByDurationF = (currentPage) => {
        currentPage -= 1;
        if(isNaN(currentPage)) {
            currentPage = 0;
        }
        if(currentPage > this.state.totalPages) {
            currentPage = this.state.totalPages;
        }
        axios.get("http://localhost:8080/employees/orderByDuration?page="+currentPage+"&size="+this.state.employeesPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    employees: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1,
                    orderByDuration: true,
                    orderByAge: false,
                    orderByGender: false,
                    orderBySalary: false
                });
            });
    }

    orderByAgeF = (currentPage) => {
        currentPage -= 1;
        if(isNaN(currentPage)) {
            currentPage = 0;
        }
        if(currentPage > this.state.totalPages) {
            currentPage = this.state.totalPages;
        }
        axios.get("http://localhost:8080/employees/orderByAge?page="+currentPage+"&size="+this.state.employeesPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    employees: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1,
                    orderByAge: true,
                    orderByDuration: false,
                    orderByGender: false,
                    orderBySalary: false
                });
            });
    }

    orderByGenderF = (currentPage) => {
        currentPage -= 1;
        if(isNaN(currentPage)) {
            currentPage = 0;
        }
        if(currentPage > this.state.totalPages) {
            currentPage = this.state.totalPages;
        }
        axios.get("http://localhost:8080/employees/orderByGender?page="+currentPage+"&size="+this.state.employeesPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    employees: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1,
                    orderByGender: true,
                    orderByDuration: false,
                    orderByAge: false,
                    orderBySalary: false
                });
            });
    }

    orderBySalaryF = (currentPage) => {
        currentPage -= 1;
        if(isNaN(currentPage)) {
            currentPage = 0;
        }
        if(currentPage > this.state.totalPages) {
            currentPage = this.state.totalPages;
        }
        axios.get("http://localhost:8080/employees/orderBySalary?page="+currentPage+"&size="+this.state.employeesPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    employees: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1,
                    orderBySalary: true,
                    orderByDuration: false,
                    orderByAge: false,
                    orderByGender: false
                });
            });
    }

    render() {
        const {employees, currentPage, totalPages, search, kindAnimal, beginDate, endDate, animalSearch} = this.state;

        return (
            <div>
                <div style={{"display": this.state.show ? "block" : "none"}} align="right">
                    <MyToast show={ this.state.show} message={this.state.message}/>
                </div>
                <Card style={{ width: '100rem' }} className="text-center" align="center">
                    <Card.Header>
                        <div style={{"float": "left"}}><h4>
                            Список Сотрудников</h4>
                        </div>
                        <div style={{"float":"right"}}>
                            <InputGroup size="sm">
                                <Form.Control required as="select"
                                              custom onChange={this.searchChange}
                                              name="search" value={search}>
                                    {this.state.categories.map(category =>
                                        <option key={category.value} value={category.value}>
                                            {category.display}
                                        </option>
                                    )}
                                </Form.Control>
                                <InputGroup.Append>
                                    <Button size="sm" variant="outline-info" type="button"
                                            disabled={animalSearch === true}
                                            onClick={this.searchData}>
                                        <FontAwesomeIcon icon={faSearch}/>
                                    </Button>
                                    <Button size="sm" variant="outline-danger" type="button" onClick={this.cancelSearch}>
                                        <FontAwesomeIcon icon={faTimes} />
                                    </Button>
                                </InputGroup.Append>
                            </InputGroup>
                        </div>
                        <div><h5>
                            <br></br>
                            <br></br>
                            Форма для поиска ответсвенных за животное вида (если не ввести даты, то имеющих доступ к)
                            <br></br>
                            <br></br></h5>
                        </div>
                        <Form onSubmit={this.submitForm} id="animalEmployeeFormId">
                            <Form.Row>
                                <Form.Group as={Col} controlId="formGridKindAnimal">
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="kindAnimal"
                                                  value={kindAnimal} onChange={this.searchChange}
                                                  placeholder="Введите вид"/>
                                </Form.Group>
                                <Form.Group as={Col} md="3" controlId="formGridBegin">
                                    <Form.Label>Дата начала</Form.Label>
                                    <DatePicker
                                        selected={beginDate}
                                        onChange={this.handleChange}
                                    />
                                </Form.Group>
                                <Form.Group as={Col} md="3" controlId="formGridEnd">
                                    <Form.Label>Дата конца</Form.Label>
                                    <DatePicker
                                        selected={endDate}
                                        onChange={this.handleEndChange}
                                    />
                                </Form.Group>
                            </Form.Row>
                            <Button size="sm" variant="success" type="submit">
                                <FontAwesomeIcon icon={faSave}/> Искать
                            </Button>{' '}
                            <Button size="sm" variant="outline-danger" type="button" onClick={this.cancelAnimalSearch}>
                                <FontAwesomeIcon icon={faTimes} />
                            </Button>
                        </Form>
                    </Card.Header>
                    <Card.Body>
                        <Table>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Фамилия</th>
                                <th>Имя</th>
                                <th>Отчество</th>
                                <th>Должность</th>
                                <th>Продолжительность работы</th>
                                <th>Возраст</th>
                                <th>Пол</th>
                                <th>Зарплата</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                employees.length === 0 ?
                                    <tr align="center">
                                        <td colSpan="10">Сотрудников нет</td>
                                    </tr>
                                    : employees.map((employee) => (
                                        <tr key={employee.id}>
                                            <td align="center">{employee.id}</td>
                                            <td align="center">{employee.surname}</td>
                                            <td align="center">{employee.name}</td>
                                            <td align="center">{employee.patronymic}</td>
                                            <td align="center">{employee.category}</td>
                                            <td align="center">{employee.durationWork}</td>
                                            <td align="center">{employee.age}</td>
                                            <td align="center">{employee.gender}</td>
                                            <td align="center">{employee.salary}</td>
                                            <td align="right">
                                                <ButtonGroup>
                                                    <Link to={"editEmployee/"+employee.id} className="btn btn-sm btn-outline-primary"><FontAwesomeIcon icon={faEdit} /></Link>{' '}
                                                    <Button size="sm" variant="outline-danger"
                                                            onClick={this.deleteEmployee.bind(this, employee.id)}><FontAwesomeIcon
                                                        icon={faTimes}/></Button>
                                                </ButtonGroup>
                                            </td>
                                        </tr>
                                    ))
                            }
                            </tbody>
                        </Table>
                        <div style={{"float": "right"}}>
                            Сортировать по
                            <InputGroup size="sm">
                                <InputGroup.Prepend>
                                    <Button type="button" variant="outline-info"
                                            disabled={search !== '' || animalSearch === true}
                                            onClick={this.orderByDurationF}>Продолжительность
                                    </Button>
                                    <Button type="button" variant="outline-info"
                                            disabled={search !== '' || animalSearch === true}
                                            onClick={this.orderByAgeF}>Возраст
                                    </Button>
                                    <Button type="button" variant="outline-info"
                                            disabled={search !== '' || animalSearch === true}
                                            onClick={this.orderByGenderF}>Пол
                                    </Button>
                                    <Button type="button" variant="outline-info"
                                            disabled={search !== '' || animalSearch === true}
                                            onClick={this.orderBySalaryF}>Зарплата
                                    </Button>
                                </InputGroup.Prepend>
                                {/*<FormControl name="currentPage" value={currentPage}*/}
                                {/*             onChange={this.changePage}/>*/}
                                {/*<InputGroup.Append>*/}
                                {/*    <Button type="button" variant="outline-info"*/}
                                {/*            disabled={currentPage === totalPages}*/}
                                {/*            onClick={this.nextPage}>*/}
                                {/*        <FontAwesomeIcon icon={faStepForward}/> Next*/}
                                {/*    </Button>*/}
                                {/*    <Button type="button" variant="outline-info"*/}
                                {/*            disabled={currentPage === totalPages}*/}
                                {/*            onClick={this.lastPage}>*/}
                                {/*        <FontAwesomeIcon icon={faFastForward}/> Last*/}
                                {/*    </Button>*/}
                                {/*</InputGroup.Append>*/}
                            </InputGroup>
                        </div>
                    </Card.Body>
                    {
                        employees.length > 0 ?
                            <Card.Footer>
                                <div style={{"float": "center"}}>
                                    Всего: {this.state.countEmployees}
                                </div>
                                <div style={{"float": "left"}}>
                                    Страница {currentPage} из {totalPages}
                                </div>
                                <div style={{"float": "right"}}>
                                    <InputGroup size="sm">
                                        <InputGroup.Prepend>
                                            <Button type="button" variant="outline-info"
                                                    disabled={currentPage === 1}
                                                    onClick={this.firstPage}>
                                                <FontAwesomeIcon icon={faFastBackward}/> First
                                            </Button>
                                            <Button type="button" variant="outline-info"
                                                    disabled={currentPage === 1}
                                                    onClick={this.prevPage}>
                                                <FontAwesomeIcon icon={faStepBackward}/> Prev
                                            </Button>
                                        </InputGroup.Prepend>
                                        <FormControl name="currentPage" value={currentPage}
                                                     onChange={this.changePage}/>
                                        <InputGroup.Append>
                                            <Button type="button" variant="outline-info"
                                                    disabled={currentPage === totalPages}
                                                    onClick={this.nextPage}>
                                                <FontAwesomeIcon icon={faStepForward}/> Next
                                            </Button>
                                            <Button type="button" variant="outline-info"
                                                    disabled={currentPage === totalPages}
                                                    onClick={this.lastPage}>
                                                <FontAwesomeIcon icon={faFastForward}/> Last
                                            </Button>
                                        </InputGroup.Append>
                                    </InputGroup>
                                </div>
                            </Card.Footer> : null
                    }
                </Card>
                {/*<Dialog open={this.state.open} onClose={this.handleClose} aria-labelledby="form-dialog-title">*/}
                {/*    <DialogTitle id="form-dialog-title">Введите вес</DialogTitle>*/}
                {/*    <DialogContent>*/}
                {/*        <TextField*/}
                {/*            onChange={this.setWeight}*/}
                {/*            autoFocus*/}
                {/*            margin="dense"*/}
                {/*            id="name"*/}
                {/*            type="number"*/}
                {/*            InputProps={{inputProps: {min: 0}}}*/}
                {/*            fullWidth*/}
                {/*        />*/}
                {/*    </DialogContent>*/}
                {/*    <DialogActions>*/}
                {/*        <Button onClick={this.handleClose} color="primary">*/}
                {/*            Отмена*/}
                {/*        </Button>*/}
                {/*        <Button onClick={this.addFood.bind(this)} color="primary">*/}
                {/*            Ок*/}
                {/*        </Button>*/}
                {/*    </DialogActions>*/}
                {/*</Dialog>*/}
            </div>
        );
    }
}