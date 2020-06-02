import React, {Component} from 'react';

import {Button, Card, Col, Form} from 'react-bootstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faList, faSave} from '@fortawesome/free-solid-svg-icons';
import MyToast from './MyToast';
import axios from 'axios';
import "react-datepicker/dist/react-datepicker.css";

export default class Zoos extends Component {

    constructor(props) {
        super(props);
        this.state = this.initialState;
        this.state = {
            message: '',
            show: false
        };
        this.zooChange = this.zooChange.bind(this);
        this.submitZoo = this.submitZoo.bind(this);
    }

    initialState = {
        id: '',
        name: ''
    };

    componentDidMount() {
        const zooId = +this.props.match.params.id;
        if (zooId) {
            this.findZooById(zooId);
        }
    }

    findZooById = (zooId) => {
        axios.get("http://localhost:8080/zoos/zooById?id=" + zooId)
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

    submitZoo = event => {
        event.preventDefault();

        const zoo = {
            name: this.state.name
        };

        axios.post("http://localhost:8080/zoos/createZoo", zoo)
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

    updateZoo = event => {
        event.preventDefault();

        const zoo = {
            id: this.state.id,
            name: this.state.name
        };

        axios.put("http://localhost:8080/zoos/updateZoo", zoo)
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

    zooChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    zoosList = () => {
        return this.props.history.push("/zoosList");
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
                        {this.state.id ? "Изменить данные о зоопарке" : "Добавить зоопарк"}
                    </Card.Header>
                    <Form onSubmit={this.state.id ? this.updateZoo : this.submitZoo} id="zooFormId">
                        <Card.Body>
                            <Form.Row>
                                <Form.Group as={Col} controlId="formGridName">
                                    <Form.Label>Название</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="name"
                                                  value={name} onChange={this.zooChange}
                                                  placeholder="Введите название"/>
                                </Form.Group>
                            </Form.Row>
                        </Card.Body>
                        <Card.Footer style={{"textAlign": "right"}}>
                            <Button size="sm" variant="success" type="submit">
                                <FontAwesomeIcon icon={faSave}/> {this.state.id ? "Изменить" : "Добавить"}
                            </Button>{' '}
                            <Button size="sm" variant="info" type="button" onClick={this.zoosList.bind()}>
                                <FontAwesomeIcon icon={faList}/> Список зоопарков
                            </Button>
                        </Card.Footer>
                    </Form>
                </Card>
            </div>
        );
    }
}