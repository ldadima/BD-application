import React, {Component} from 'react';

import {Button, Card, Col, Form} from 'react-bootstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faList, faSave} from '@fortawesome/free-solid-svg-icons';
import MyToast from './MyToast';
import axios from 'axios';
import "react-datepicker/dist/react-datepicker.css";

export default class Feeds extends Component {

    constructor(props) {
        super(props);
        this.state = this.initialState;
        this.state = {
            message: '',
            show: false
        };
        this.feedChange = this.feedChange.bind(this);
        this.submitFeed = this.submitFeed.bind(this);
    }

    initialState = {
        id: '',
        name: '',
        foodType: '',
        stock: 0,
        volumeIndependentProduction: 0
    };

    componentDidMount() {
        const feedId = +this.props.match.params.id;
        if (feedId) {
            this.findFeedById(feedId);
        }
    }

    findFeedById = (feedId) => {
        axios.get("http://localhost:8080/feeds/feedById?id=" + feedId)
            .then(response => {
                if (response.data != null) {
                    this.setState({
                        id: response.data.id,
                        name: response.data.name,
                        foodType: response.data.type,
                        stock: response.data.stock,
                        volumeIndependentProduction: response.data.volumeIndependentProduction
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

    submitFeed = event => {
        event.preventDefault();

        const feed = {
            name: this.state.name,
            type: this.state.foodType,
            stock: this.state.stock,
            volumeIndependentProduction: this.state.volumeIndependentProduction
        };

        axios.post("http://localhost:8080/feeds/createFeed", feed)
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

    updateFeed = event => {
        event.preventDefault();

        const feed = {
            id: this.state.id,
            name: this.state.name,
            type: this.state.foodType,
            stock: this.state.stock,
            volumeIndependentProduction: this.state.volumeIndependentProduction
        };

        axios.put("http://localhost:8080/feeds/updateFeed", feed)
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

    feedChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    feedsList = () => {
        return this.props.history.push("/feedsList");
    };

    render() {
        const {name, foodType, stock, volumeIndependentProduction} = this.state;

        return (
            <div>
                <div style={{"display": this.state.show ? "block" : "none"}} align="right">
                    <MyToast show={this.state.show} message = {this.state.message}/>
                </div>
                <Card>
                    <Card.Header>
                        {this.state.id ? "Изменить данные о еде" : "Добавить еду"}
                    </Card.Header>
                    <Form onSubmit={this.state.id ? this.updateFeed : this.submitFeed} id="feedFormId">
                        <Card.Body>
                            <Form.Row>
                                <Form.Group as={Col} controlId="formGridName">
                                    <Form.Label>Название</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="name"
                                                  value={name} onChange={this.feedChange}
                                                  placeholder="Введите название"/>
                                </Form.Group>
                                <Form.Group as={Col} controlId="formGridFoodType">
                                    <Form.Label>Вид</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="foodType"
                                                  value={foodType} onChange={this.feedChange}
                                                  placeholder="Введите вид еды"/>
                                </Form.Group>
                                <Form.Group as={Col} md="4" controlId="formGridStock">
                                    <Form.Label>На складе</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="number" name="stock"
                                                  min="0"
                                                  value={stock} onChange={this.feedChange}
                                                  placeholder="Введите количество на складе"/>
                                </Form.Group>
                                <Form.Group as={Col} md="4" controlId="formGridVolumeIndependentProduction">
                                    <Form.Label>Производится</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="number" name="volumeIndependentProduction"
                                                  min="0"
                                                  value={volumeIndependentProduction} onChange={this.feedChange}
                                                  placeholder="Введите объем производства"/>
                                </Form.Group>
                            </Form.Row>
                        </Card.Body>
                        <Card.Footer style={{"textAlign": "right"}}>
                            <Button size="sm" variant="success" type="submit">
                                <FontAwesomeIcon icon={faSave}/> {this.state.id ? "Изменить" : "Добавить"}
                            </Button>{' '}
                            <Button size="sm" variant="info" type="button" onClick={this.feedsList.bind()}>
                                <FontAwesomeIcon icon={faList}/> Список еды
                            </Button>
                        </Card.Footer>
                    </Form>
                </Card>
            </div>
        );
    }
}