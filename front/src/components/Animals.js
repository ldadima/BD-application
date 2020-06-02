import React, {Component} from 'react';

import {Button, Card, Col, Form} from 'react-bootstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faList, faSave} from '@fortawesome/free-solid-svg-icons';
import MyToast from './MyToast';
import axios from 'axios';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import moment from 'moment';

export default class Animals extends Component {

    constructor(props) {
        super(props);
        this.state = this.initialState;
        this.state = {
            message: '',
            show: false,
            animalTypes: [],
            climaticHabitats: [],
            genders: [],
            physicalStates: [],
            developments: []
        };
        this.animalChange = this.animalChange.bind(this);
        this.submitAnimal = this.submitAnimal.bind(this);
        this.changeNeedRelocation = this.changeNeedRelocation.bind(this);
    }

    initialState = {
        id: '',
        name: '',
        kindAnimal: '',
        animalType: '',
        climaticHabitat: '',
        gender: '',
        physicalState: '',
        progeny: 0,
        birthday: new Date(),
        departureDate: null,
        departureReason: '',
        needRelocation: 'false',
        height: 0,
        weight: 0,
        development: '',
        needHospital: 'false',
        dateLast: null
    };

    componentDidMount() {
        const animalId = +this.props.match.params.id;
        if (animalId) {
            this.findAnimalById(animalId);
        }
        this.findAllClimaticHabitats();
        this.findAllGenders();
        this.findAllPhysicalStates();
        this.findAllTypes();
        this.findAllDevelopment();
    }

    findAllTypes = () => {
        axios.get("http://localhost:8080/type/typeAnimal")
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    animalTypes: [{value: '', display: 'Выберите тип'}]
                        .concat(data.map(animalType => {
                            return {value: animalType, display: animalType}
                        }))
                });
            });
    };

    findAllClimaticHabitats = () => {
        axios.get("http://localhost:8080/type/climaticHabitat")
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    climaticHabitats: [{value: '', display: 'Выберите зону обитания'}]
                        .concat(data.map(climaticHabitat => {
                            return {value: climaticHabitat, display: climaticHabitat}
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

    findAllPhysicalStates = () => {
        axios.get("http://localhost:8080/type/physicalState")
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    physicalStates: [{value: '', display: 'Выберите физическое состояние'}]
                        .concat(data.map(physicalState => {
                            return {value: physicalState, display: physicalState}
                        }))
                });
            });
    };

    findAllDevelopment = () => {
        axios.get("http://localhost:8080/type/development")
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    developments: [{value: '', display: 'Выберите зрелость'}]
                        .concat(data.map(development => {
                            return {value: development, display: development}
                        }))
                });
            });
    };

    findAnimalById = (animalId) => {
        axios.get("http://localhost:8080/animals/animalById?id=" + animalId)
            .then(response => {
                if (response.data != null) {
                    this.setState({
                        id: response.data.animal.id,
                        name: response.data.animal.name,
                        kindAnimal: response.data.animal.kindAnimal,
                        animalType: response.data.animal.type,
                        climaticHabitat: response.data.animal.climaticHabitat,
                        gender: response.data.animal.gender,
                        physicalState: response.data.animal.physicalState,
                        progeny: response.data.animal.progeny,
                        birthday: new Date(moment(response.data.animal.birthday).format('MM/DD/YYYY')),
                        departureDate: response.data.animal.departureDate !== null ? new Date(moment(response.data.animal.departureDate).format('MM/DD/YYYY')) : null,
                        departureReason: response.data.animal.departureReason,
                        needRelocation: response.data.animal.needRelocation ? 'true' : 'false',
                        height: response.data.height !== null ? response.data.height : null,
                        weight: response.data.weight !== null ? response.data.weight : null,
                        development: response.data.development !== null ? response.data.development : null,
                        needHospital: response.data.needHospital !== null ? response.data.needHospital !== false ? 'true' : 'false' : 'false',
                        dateLast: response.data.dateLast !== null ? new Date(moment(response.data.dateLast).format('MM/DD/YYYY')) : null
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

    submitAnimal = event => {
        event.preventDefault();

        const animal = {
            name: this.state.name,
            kindAnimal: this.state.kindAnimal,
            animalType: this.state.animalType,
            climaticHabitat: this.state.climaticHabitat,
            gender: this.state.gender,
            physicalState: this.state.physicalState,
            progeny: this.state.progeny,
            birthday: moment(this.state.birthday).format('YYYY-MM-DD'),
            departureDate: this.state.departureDate === null ? null : moment(this.state.departureDate).format('YYYY-MM-DD'),
            departureReason: this.state.departureReason,
            needRelocation: this.state.needRelocation === 'true'
        };

        const info = {
            animal: animal,
            height: this.state.height,
            weight: this.state.weight,
            development: this.state.development,
            needHospital: this.state.needHospital === 'true',
            dateLast: moment(this.state.dateLast).format('YYYY-MM-DD')
        }

        axios.post("http://localhost:8080/animals/createAnimal", info)
            .then(response => {
                this.setState({"show": true, "message": 'Добавление успешно'});
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

    updateAnimal = event => {
        event.preventDefault();

        const animal = {
            id: this.state.id,
            name: this.state.name,
            kindAnimal: this.state.kindAnimal,
            type: this.state.animalType,
            climaticHabitat: this.state.climaticHabitat,
            gender: this.state.gender,
            physicalState: this.state.physicalState,
            progeny: this.state.progeny,
            birthday: moment(this.state.birthday).format('YYYY-MM-DD'),
            departureDate: this.state.departureDate === null ? null : moment(this.state.departureDate).format('YYYY-MM-DD'),
            departureReason: this.state.departureReason,
            needRelocation: this.state.needRelocation === 'true'
        };

        const info = {
            animal: animal,
            height: this.state.height,
            weight: this.state.weight,
            development: this.state.development,
            needHospital: this.state.needHospital === 'true',
            dateLast: moment(this.state.dateLast).format('YYYY-MM-DD')
        }

        axios.put("http://localhost:8080/animals/updateAnimal", info)
            .then(response => {
                if (response.data != null) {
                    this.setState({"show": true, "message": 'Изменено успешно'});
                    setTimeout(() => this.setState({"show": false}), 3000);
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

    animalChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    };

    animalsList = () => {
        return this.props.history.push("/animalsList");
    };

    changeNeedRelocation = (e) => {
        this.setState({
            "needRelocation": e.target.value
        })
    }

    changeNeedHospital = (e) => {
        this.setState({
            "needHospital": e.target.value
        })
    }

    handleChange = date => {
        this.setState({
            "birthday": date
        });
    };

    departureChange = date => {
        this.setState({
            "departureDate": date
        });
    };

    dateLastChange = date => {
        this.setState({
            "dateLast": date
        });
    };

    render() {
        const {name, kindAnimal, animalType, climaticHabitat, gender, physicalState, progeny, birthday, departureDate, departureReason, needRelocation, height, weight, development, needHospital, dateLast} = this.state;

        return (
            <div>
                <div style={{"display": this.state.show ? "block" : "none"}} align="right">
                    <MyToast show={this.state.show} message={this.state.message}/>
                </div>
                <Card>
                    <Card.Header>
                        {this.state.id ? "Изменить данные о животном" : "Добавить животное"}
                    </Card.Header>
                    <Form onSubmit={this.state.id ? this.updateAnimal : this.submitAnimal} id="animalFormId">
                        <Card.Body>
                            <Form.Row>
                                <Form.Group as={Col} controlId="formGridName">
                                    <Form.Label>Название</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="name"
                                                  value={name} onChange={this.animalChange}
                                                  placeholder="Введите имя"/>
                                </Form.Group>
                                <Form.Group as={Col} controlId="formGridKindAnimal">
                                    <Form.Label>Вид</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="kindAnimal"
                                                  value={kindAnimal} onChange={this.animalChange}
                                                  placeholder="Введите вид"/>
                                </Form.Group>
                                <Form.Group as={Col} controlId="formGridAnimalType">
                                    <Form.Label>Тип</Form.Label>
                                    <Form.Control required as="select"
                                                  custom onChange={this.animalChange}
                                                  name="animalType" value={animalType}>
                                        {this.state.animalTypes.map(animalType =>
                                            <option key={animalType.value} value={animalType.value}>
                                                {animalType.display}
                                            </option>
                                        )}
                                    </Form.Control>
                                </Form.Group>
                            </Form.Row>
                            <Form.Row>
                                <Form.Group as={Col} controlId="formGridClimaticHabitat">
                                    <Form.Label>Зона обитания</Form.Label>
                                    <Form.Control required as="select"
                                                  custom onChange={this.animalChange}
                                                  name="climaticHabitat" value={climaticHabitat}>
                                        {this.state.climaticHabitats.map(climaticHabitat =>
                                            <option key={climaticHabitat.value} value={climaticHabitat.value}>
                                                {climaticHabitat.display}
                                            </option>
                                        )}
                                    </Form.Control>
                                </Form.Group>
                                <Form.Group as={Col} controlId="formGridGender">
                                    <Form.Label>Пол</Form.Label>
                                    <Form.Control required as="select"
                                                  custom onChange={this.animalChange}
                                                  name="gender" value={gender}>
                                        {this.state.genders.map(gender =>
                                            <option key={gender.value} value={gender.value}>
                                                {gender.display}
                                            </option>
                                        )}
                                    </Form.Control>
                                </Form.Group>
                                <Form.Group as={Col} controlId="formGridPhysicalState">
                                    <Form.Label>Состояние здоровья</Form.Label>
                                    <Form.Control required as="select"
                                                  custom onChange={this.animalChange}
                                                  name="physicalState" value={physicalState}>
                                        {this.state.physicalStates.map(physicalState =>
                                            <option key={physicalState.value} value={physicalState.value}>
                                                {physicalState.display}
                                            </option>
                                        )}
                                    </Form.Control>
                                </Form.Group>
                            </Form.Row>
                            <Form.Row>
                                <Form.Group as={Col} md="4" controlId="formGridProgeny">
                                    <Form.Label>Потомство </Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="number" name="progeny"
                                                  min="0"
                                                  value={progeny} onChange={this.animalChange}
                                                  placeholder="Введите объем потомства"/>
                                </Form.Group>
                                <Form.Group as={Col} md="3" controlId="formGridBirthday">
                                    <Form.Label>Дата Рождения</Form.Label>
                                    <DatePicker
                                        selected={birthday}
                                        onChange={this.handleChange}
                                    />
                                </Form.Group>
                                <Form.Group as={Col} md="3" controlId="formGridDepartureDate">
                                    <Form.Label>Дата департации</Form.Label>
                                    <DatePicker
                                        selected={departureDate}
                                        onChange={this.departureChange}
                                    />
                                </Form.Group>
                            </Form.Row>
                            <Form.Row>
                                <Form.Group as={Col} md="7" controlId="formGridDepartureReason">
                                    <Form.Label>Причина департации</Form.Label>
                                    <Form.Control
                                        type="test" name="departureReason"
                                        value={departureReason} onChange={this.animalChange}
                                        placeholder="Введите причину департации"/>
                                </Form.Group>
                                <Form.Group as={Col} md="5" controlId="formGridNeedRelocation" required
                                            autoComplete="off">
                                    <Form.Label column="none">Переселение</Form.Label>
                                    <input type="radio" value="true" checked={needRelocation === "true"}
                                           onChange={this.changeNeedRelocation}/>Требуется<br></br>
                                    <input type="radio" value="false" checked={needRelocation === "false"}
                                           onChange={this.changeNeedRelocation}/>Не требуется
                                </Form.Group>
                            </Form.Row>
                            <div><h5 align="center">Медицинская карта</h5></div>
                            <Form.Row>
                                <Form.Group as={Col} md="4" controlId="formGridHeight">
                                    <Form.Label>Рост</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="number" name="height"
                                                  min="0"
                                                  value={height} onChange={this.animalChange}
                                                  placeholder="Введите рост"/>
                                </Form.Group>
                                <Form.Group as={Col} md="4" controlId="formGridWeight">
                                    <Form.Label>Вес</Form.Label>
                                    <Form.Control required autoComplete="off"
                                                  type="number" name="weight"
                                                  min="0"
                                                  value={weight} onChange={this.animalChange}
                                                  placeholder="Введите вес"/>
                                </Form.Group>
                                <Form.Group as={Col} controlId="formGridDevelopment">
                                    <Form.Label>Зрелость</Form.Label>
                                    <Form.Control required as="select"
                                                  custom onChange={this.animalChange}
                                                  name="development" value={development}>
                                        {this.state.developments.map(development =>
                                            <option key={development.value} value={development.value}>
                                                {development.display}
                                            </option>
                                        )}
                                    </Form.Control>
                                </Form.Group>
                            </Form.Row>
                            <Form.Row>
                                <Form.Group as={Col} md="5" controlId="formGridNeedHospital" required
                                            autoComplete="off">
                                    <Form.Label align="left" column="none">Госпитализация</Form.Label>
                                    <input type="radio" value="true" checked={needHospital === "true"}
                                           onChange={this.changeNeedHospital}/>Требуется<br></br>
                                    <input type="radio" value="false" checked={needHospital === "false"}
                                           onChange={this.changeNeedHospital}/>Не требуется
                                </Form.Group>
                                <Form.Group as={Col} md="3" controlId="formGridDateLast">
                                    <Form.Label>Дата последнего осмотра</Form.Label>
                                    <DatePicker
                                        selected={dateLast}
                                        onChange={this.dateLastChange}
                                    />
                                </Form.Group>
                            </Form.Row>
                        </Card.Body>
                        <Card.Footer style={{"textAlign": "right"}}>
                            <Button size="sm" variant="success" type="submit">
                                <FontAwesomeIcon icon={faSave}/> {this.state.id ? "Изменить" : "Добавить"}
                            </Button>{' '}
                            <Button size="sm" variant="info" type="button" onClick={this.animalsList.bind()}>
                                <FontAwesomeIcon icon={faList}/> Список животных
                            </Button>
                        </Card.Footer>
                    </Form>
                </Card>
            </div>
        );
    }
}