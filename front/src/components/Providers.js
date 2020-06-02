import React, {Component} from 'react';

import {Button, Card, Col, Form} from 'react-bootstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faList, faSave} from '@fortawesome/free-solid-svg-icons';
import MyToast from './MyToast';
import axios from 'axios';
import "react-datepicker/dist/react-datepicker.css";
import moment from "moment";
import DatePicker from "react-datepicker";

export default class Providers extends Component {

    constructor(props) {
        super(props);
        this.state = this.initialState;
        this.state = {
            message: '',
            show: false
        };
        this.providerChange = this.providerChange.bind(this);
        this.submitProvider = this.submitProvider.bind(this);
    }

    initialState = {
        provId: '',
        name: '',
        dateBegin: new Date(),
        dateEnd: null
    };

    componentDidMount() {
        const providerId = +this.props.match.params.id;
        if (providerId) {
            this.findProviderById(providerId);
        }
    }

    findProviderById = (providerId) => {
        axios.get("http://localhost:8080/providers/providerById?id=" + providerId)
            .then(response => {
                if (response.data != null) {
                    this.setState({
                        provId: response.data.provId,
                        name: response.data.name,
                        dateBegin: new Date(moment(response.data.dateBegin).format('MM/DD/YYYY')),
                        dateEnd: response.data.dateEnd !== null ? new Date(moment(response.data.dateEnd).format('MM/DD/YYYY')) : null
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

    submitProvider = event => {
        event.preventDefault();

        const provider = {
            name: this.state.name,
            dateBegin: moment(this.state.dateBegin).format('YYYY-MM-DD'),
            dateEnd: this.state.dateEnd === null ? null : moment(this.state.dateEnd).format('YYYY-MM-DD')
        };

        axios.post("http://localhost:8080/providers/createProvider", provider)
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

    updateProvider = event => {
        event.preventDefault();

        const provider = {
            provId: this.state.provId,
            name: this.state.name,
            dateBegin: moment(this.state.dateBegin).format('YYYY-MM-DD'),
            dateEnd: this.state.dateEnd === null ? null : moment(this.state.dateEnd).format('YYYY-MM-DD')
        };

        axios.put("http://localhost:8080/providers/updateProvider", provider)
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

    providerChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    providersList = () => {
        return this.props.history.push("/providersList");
    };

    handleBeginChange = date => {
        this.setState({
            "dateBegin": date
        });
    };

    handleEndChange = date => {
        this.setState({
            "dateEnd": date
        });
    };

    render() {
        const {name, dateBegin, dateEnd} = this.state;

        return (
            <div>
                <div style={{"display": this.state.show ? "block" : "none"}} align="right">
                    <MyToast show={this.state.show} message = {this.state.message}/>
                </div>
                <Card>
                    <Card.Header>
                        {this.state.provId ? "Изменить данные о поставщике" : "Добавить поставщика"}
                    </Card.Header>
                    <Form onSubmit={this.state.provId ? this.updateProvider : this.submitProvider} id="providerFormId">
                        <Card.Body>
                            <Form.Row>
                                <Form.Group as={Col} controlId="formGridName">
                                    <Form.Label>Наименование</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="name"
                                                  value={name} onChange={this.providerChange}
                                                  placeholder="Введите наименование"/>
                                </Form.Group>
                                <Form.Group as={Col} md="3" controlId="formGridDateBegin">
                                    <Form.Label>Начало сотрудничества</Form.Label>
                                    <DatePicker
                                        selected={dateBegin}
                                        onChange={this.handleBeginChange}
                                    />
                                </Form.Group>
                                <Form.Group as={Col} md="3" controlId="formGridDateEnd">
                                    <Form.Label>Окончание сотрудничества</Form.Label>
                                    <DatePicker
                                        selected={dateEnd}
                                        onChange={this.handleEndChange}
                                    />
                                </Form.Group>
                            </Form.Row>
                        </Card.Body>
                        <Card.Footer style={{"textAlign": "right"}}>
                            <Button size="sm" variant="success" type="submit">
                                <FontAwesomeIcon icon={faSave}/> {this.state.provId ? "Изменить" : "Добавить"}
                            </Button>{' '}
                            <Button size="sm" variant="info" type="button" onClick={this.providersList.bind()}>
                                <FontAwesomeIcon icon={faList}/> Список поставщиков
                            </Button>
                        </Card.Footer>
                    </Form>
                </Card>
            </div>
        );
    }
}