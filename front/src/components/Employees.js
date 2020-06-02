import React, {Component} from 'react';

import {Button, Card, Col, Form} from 'react-bootstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faList, faSave} from '@fortawesome/free-solid-svg-icons';
import MyToast from './MyToast';
import axios from 'axios';
import "react-datepicker/dist/react-datepicker.css";

export default class Employees extends Component {

    constructor(props) {
        super(props);
        this.state = this.initialState;
        this.state = {
            message: '',
            show: false,
            categories: [],
            genders: []
        };
        this.employeeChange = this.employeeChange.bind(this);
        this.submitEmployee = this.submitEmployee.bind(this);
    }

    initialState = {
        id: '',
        surname: '',
        name: '',
        patronymic: '',
        category: '',
        durationWork: 0,
        age: 0,
        gender: '',
        salary: 0
    };

    componentDidMount() {
        const employeeId = +this.props.match.params.id;
        if (employeeId) {
            this.findEmployeeById(employeeId);
        }
        this.findAllCategories();
        this.findAllGenders();
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

    findAllGenders = () => {
        axios.get("http://localhost:8080/type/gender")
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    genders: [{value: '', display: 'Выберите пол'}]
                        .concat(data.map(gender => {
                            return {value: gender, display: gender}
                        }))
                });
            });
    };

    findEmployeeById = (employeeId) => {
        axios.get("http://localhost:8080/employees/employeeById?id=" + employeeId)
            .then(response => {
                if (response.data != null) {
                    this.setState({
                        id: response.data.id,
                        surname: response.data.surname,
                        name: response.data.name,
                        patronymic: response.data.patronymic,
                        category: response.data.category,
                        durationWork: response.data.durationWork,
                        age: response.data.age,
                        gender: response.data.gender,
                        salary: response.data.salary
                    });
                }
            })
            .catch(error => {
                this.setState({
                    "show": true,
                    "message": error.response.status === 400 ? error.response.data.errors[0].defaultMessage : error.response.data
                });
            });
    };

    submitEmployee = event => {
        event.preventDefault();

        const employee = {
            surname: this.state.surname,
            name: this.state.name,
            patronymic: this.state.patronymic,
            category: this.state.category,
            durationWork: this.state.durationWork,
            age: this.state.age,
            gender: this.state.gender,
            salary: this.state.salary
        };

        axios.post("http://localhost:8080/employees/createEmployee", employee)
            .then(response => {
                if (response.data != null) {
                    this.setState({"show": true, "message": 'Добавление успешно'});
                } else {
                    this.setState({"show": false});
                }
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

    updateEmployee = event => {
        event.preventDefault();

        const employee = {
            id: this.state.id,
            surname: this.state.surname,
            name: this.state.name,
            patronymic: this.state.patronymic,
            category: this.state.category,
            durationWork: this.state.durationWork,
            age: this.state.age,
            gender: this.state.gender,
            salary: this.state.salary
        };

        axios.put("http://localhost:8080/employees/updateEmployee", employee)
            .then(response => {
                this.setState({"show": true, "message": 'Изменено успешно'});
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

    employeeChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    employeesList = () => {
        return this.props.history.push("/employeesList");
    };

    render() {
        const {surname, name, patronymic, category, durationWork, age, gender, salary} = this.state;

        return (
            <div>
                <div style={{"display": this.state.show ? "block" : "none"}} align="right">
                    <MyToast show={this.state.show} message = {this.state.message}/>
                </div>
                <Card>
                    <Card.Header>
                        {this.state.id ? "Изменить данные о сотруднике" : "Добавить сотрудника"}
                    </Card.Header>
                    <Form onSubmit={this.state.id ? this.updateEmployee : this.submitEmployee} id="employeeFormId">
                        <Card.Body>
                            <Form.Row>
                                <Form.Group as={Col} controlId="formGridSurname">
                                    <Form.Label>Фамилия</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="surname"
                                                  value={surname} onChange={this.employeeChange}
                                                  placeholder="Введите фамилию"/>
                                </Form.Group>
                                <Form.Group as={Col} controlId="formGridName">
                                    <Form.Label>Имя</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="name"
                                                  value={name} onChange={this.employeeChange}
                                                  placeholder="Введите имя"/>
                                </Form.Group>
                                <Form.Group as={Col} controlId="formGridPatronymic">
                                    <Form.Label>Отчество</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="patronymic"
                                                  value={patronymic} onChange={this.employeeChange}
                                                  placeholder="Введите отчество"/>
                                </Form.Group>
                                <Form.Group as={Col} controlId="formGridCategory">
                                    <Form.Label>Должность</Form.Label>
                                    <Form.Control required as="select"
                                                  custom onChange={this.employeeChange}
                                                  name="category" value={category}>
                                        {this.state.categories.map(category =>
                                            <option key={category.value} value={category.value}>
                                                {category.display}
                                            </option>
                                        )}
                                    </Form.Control>
                                </Form.Group>
                            </Form.Row>
                            <Form.Row>
                                <Form.Group as={Col} md="4" controlId="formGridDurationWork">
                                    <Form.Label>Продолжительность работы</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="number" name="durationWork"
                                                  min="0"
                                                  max="90"
                                                  value={durationWork} onChange={this.employeeChange}
                                                  placeholder="Введите продолжительность работы"/>
                                </Form.Group>
                                <Form.Group as={Col} md="4" controlId="formGridAge">
                                    <Form.Label>Возраст</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="number" name="age"
                                                  min="18"
                                                  max="100"
                                                  value={age} onChange={this.employeeChange}
                                                  placeholder="Введите возраст"/>
                                </Form.Group>
                                <Form.Group as={Col} controlId="formGridGender">
                                    <Form.Label>Пол</Form.Label>
                                    <Form.Control required as="select"
                                                  custom onChange={this.employeeChange}
                                                  name="gender" value={gender}>
                                        {this.state.genders.map(gender =>
                                            <option key={gender.value} value={gender.value}>
                                                {gender.display}
                                            </option>
                                        )}
                                    </Form.Control>
                                </Form.Group>
                                <Form.Group as={Col} md="4" controlId="formGridSalary">
                                    <Form.Label>Зарплата</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="number" name="salary"
                                                  min="0"
                                                  value={salary} onChange={this.employeeChange}
                                                  placeholder="Введите зарплату"/>
                                </Form.Group>
                            </Form.Row>
                        </Card.Body>
                        <Card.Footer style={{"textAlign": "right"}}>
                            <Button size="sm" variant="success" type="submit">
                                <FontAwesomeIcon icon={faSave}/> {this.state.id ? "Изменить" : "Добавить"}
                            </Button>{' '}
                            <Button size="sm" variant="info" type="button" onClick={this.employeesList.bind()}>
                                <FontAwesomeIcon icon={faList}/> Список сотрудников
                            </Button>
                        </Card.Footer>
                    </Form>
                </Card>
            </div>
        );
    }
}