import React, {Component} from 'react';

import {Button, Card, Col, Form} from 'react-bootstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faList, faSave} from '@fortawesome/free-solid-svg-icons';
import MyToast from './MyToast';
import axios from 'axios';
import "react-datepicker/dist/react-datepicker.css";

export default class Illnesses extends Component {

    constructor(props) {
        super(props);
        this.state = this.initialState;
        this.state = {
            message: '',
            show: false
        };
        this.illnessChange = this.illnessChange.bind(this);
        this.submitIllness = this.submitIllness.bind(this);
    }

    initialState = {
        id: '',
        name: ''
    };

    componentDidMount() {
        const illnessId = +this.props.match.params.id;
        if (illnessId) {
            this.findIllnessById(illnessId);
        }
    }

    findIllnessById = (illnessId) => {
        axios.get("http://localhost:8080/illnesses/illnessById?id=" + illnessId)
            .then(response => {
                if (response.data != null) {
                    this.setState({
                        id: response.data.id,
                        name: response.data.name
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

    submitIllness = event => {
        event.preventDefault();

        const illness = {
            name: this.state.name
        };

        axios.post("http://localhost:8080/illnesses/createIllness", illness)
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

    updateIllness = event => {
        event.preventDefault();

        const illness = {
            id: this.state.id,
            name: this.state.name
        };

        axios.put("http://localhost:8080/illnesses/updateIllness", illness)
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

    illnessChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    illnessesList = () => {
        return this.props.history.push("/illnessesList");
    };

    render() {
        const {name} = this.state;

        return (
            <div>
                <div style={{"display": this.state.show ? "block" : "none"}} align="right">
                    <MyToast show={this.state.show} message = {this.state.message}/>
                </div>
                <Card>
                    <Card.Header>
                        {this.state.id ? "Изменить данные о болезни" : "Добавить болезнь"}
                    </Card.Header>
                    <Form onSubmit={this.state.id ? this.updateIllness : this.submitIllness} id="illnessFormId">
                        <Card.Body>
                            <Form.Row>
                                <Form.Group as={Col} controlId="formGridName">
                                    <Form.Label>Название</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="name"
                                                  value={name} onChange={this.illnessChange}
                                                  placeholder="Введите название"/>
                                </Form.Group>
                            </Form.Row>
                        </Card.Body>
                        <Card.Footer style={{"textAlign": "right"}}>
                            <Button size="sm" variant="success" type="submit">
                                <FontAwesomeIcon icon={faSave}/> {this.state.id ? "Изменить" : "Добавить"}
                            </Button>{' '}
                            <Button size="sm" variant="info" type="button" onClick={this.illnessesList.bind()}>
                                <FontAwesomeIcon icon={faList}/> Список болезней
                            </Button>
                        </Card.Footer>
                    </Form>
                </Card>
            </div>
        );
    }
}