import React from "react";
import {Button, Card, Col, Form, FormControl, InputGroup, Table} from "react-bootstrap";
import axios from 'axios';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faFastBackward,
    faFastForward, faSave,
    faStepBackward,
    faStepForward, faTimes
} from '@fortawesome/free-solid-svg-icons'
import MyToast from "./MyToast";
import {faEdit} from "@fortawesome/free-regular-svg-icons";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import {Link} from "react-router-dom";

export default class AnimalsList extends React.Component {

    constructor(props) {
        super(props);
        this.state = this.initialState;
        this.state = {
            animals: [],
            currentPage: 1,
            animalsPerPage: 10,
            animalId: 0,
            show: false,
            message: '',
            ageSearch: false,
            illnessSearch: false,
            vaccineSearch: false,
            childSearch: false,
            relocationSearch: false,
            kindSearch: false,
            warmSearch: false,
            seasonSearch: false,
            seasons: []
        };
        this.searchChange = this.searchChange.bind(this);
        this.submitForm = this.submitForm.bind(this);
    }

    initialState = {
        age: 0,
        illness: '',
        vaccine: '',
        kind: '',
        food: '',
        season:''
    };


    componentDidMount() {
        this.findAllAnimal(this.state.currentPage);
        this.findAllSeasons();

    }

    findAllSeasons = () => {
        axios.get("http://localhost:8080/type/season")
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    seasons: [{value: '', display: 'Выберите сезон'}]
                        .concat(data.map(season => {
                            return {value: season, display: season}
                        }))
                });
            });
    };

    findAllAnimal(currentPage) {
        currentPage -= 1;
        axios.get("http://localhost:8080/animals/showAll?page=" + currentPage + "&size=" + this.state.animalsPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    animals: data.content,
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

    submitFormAge = event => {
        event.preventDefault();
        this.setState({ageSearch : true}, () => {
            this.submitForm(1);
        });
    };

    submitFormIll = event => {
        event.preventDefault();
        this.setState({illnessSearch : true}, () => {
            this.submitForm(1);
        });
    };

    submitFormVaccine = event => {
        event.preventDefault();
        this.setState({vaccineSearch : true}, () => {
            this.submitForm(1);
        });
    };

    submitFormKind = event => {
        event.preventDefault();
        this.setState({kindSearch : true}, () => {
            this.submitForm(1);
        });
    };

    submitFormRelocation = event => {
        event.preventDefault();
        this.setState({relocationSearch : true}, () => {
            this.submitForm(1);
        });
    };

    submitFormWarm = event => {
        event.preventDefault();
        this.setState({warmSearch : true}, () => {
            this.submitForm(1);
        });
    };

    submitFormChild = event => {
        event.preventDefault();
        this.setState({childSearch : true}, () => {
            this.submitForm(1);
        });
    };

    submitFormSeason = event => {
        event.preventDefault();
        this.setState({seasonSearch : true}, () => {
            this.submitForm(1);
        });
    };

    submitForm(currentPage) {
        currentPage -= 1;
        if(isNaN(currentPage)) {
            currentPage = 0;
        }
        if(currentPage > this.state.totalPages) {
            currentPage = this.state.totalPages;
        }
        if(this.state.ageSearch){
            const info = {
                page: currentPage,
                size: this.state.animalsPerPage,
                age: this.state.age
            }
            axios.get("http://localhost:8080/animals/needWarmAnimals?page=" + info.page +"&size=" + info.size +"&age=" + info.age)
                .then(response => response.data)
                .then((data) => {
                    this.setState({
                        animals: data.content,
                        totalPages: data.totalPages,
                        totalElements: data.totalElements,
                        currentPage: data.number + 1,
                        ageSearch: true
                    })
                })
            setTimeout(() => this.setState({"show": false}), 3000);
            this.setState(this.initialState);
        } else if(this.state.illnessSearch){
            const info = {
                page: currentPage,
                size: this.state.animalsPerPage,
                illness: this.state.illness
            }
            axios.get("http://localhost:8080/animals/givenIllnessAnimals?page=" + info.page +"&size=" + info.size +"&illness=" + info.illness)
                .then(response => response.data)
                .then((data) => {
                    this.setState({
                        animals: data.content,
                        totalPages: data.totalPages,
                        totalElements: data.totalElements,
                        currentPage: data.number + 1,
                        illnessSearch: true
                    })
                })
            setTimeout(() => this.setState({"show": false}), 3000);
            this.setState(this.initialState);
        } else if(this.state.vaccineSearch) {
            const info = {
                page: currentPage,
                size: this.state.animalsPerPage,
                vaccine: this.state.vaccine
            }
            axios.get("http://localhost:8080/animals/givenVaccineAnimals?page=" + info.page +"&size=" + info.size +"&vaccine=" + info.vaccine)
                .then(response => response.data)
                .then((data) => {
                    this.setState({
                        animals: data.content,
                        totalPages: data.totalPages,
                        totalElements: data.totalElements,
                        currentPage: data.number + 1,
                        vaccineSearch: true
                    })
                })
            setTimeout(() => this.setState({"show": false}), 3000);
            this.setState(this.initialState);
        } else if(this.state.kindSearch) {
            const info = {
                page: currentPage,
                size: this.state.animalsPerPage,
                kind: this.state.kind
            }
            axios.get("http://localhost:8080/animals/compatibilityKindAnimals?page=" + info.page +"&size=" + info.size +"&kind=" + info.kind)
                .then(response => response.data)
                .then((data) => {
                    this.setState({
                        animals: data.content,
                        totalPages: data.totalPages,
                        totalElements: data.totalElements,
                        currentPage: data.number + 1,
                        kindSearch: true
                    })
                })
            setTimeout(() => this.setState({"show": false}), 3000);
            this.setState(this.initialState);
        } else if(this.state.relocationSearch) {
            const info = {
                page: currentPage,
                size: this.state.animalsPerPage
            }
            axios.get("http://localhost:8080/animals/needRelocationAnimals?page=" + info.page +"&size=" + info.size)
                .then(response => response.data)
                .then((data) => {
                    this.setState({
                        animals: data.content,
                        totalPages: data.totalPages,
                        totalElements: data.totalElements,
                        currentPage: data.number + 1,
                        relocationSearch: true
                    })
                })
            setTimeout(() => this.setState({"show": false}), 3000);
            this.setState(this.initialState);
        } else if(this.state.warmSearch) {
            const info = {
                page: currentPage,
                size: this.state.animalsPerPage
            }
            axios.get("http://localhost:8080/animals/needWarmCellAnimals?page=" + info.page +"&size=" + info.size)
                .then(response => response.data)
                .then((data) => {
                    this.setState({
                        animals: data.content,
                        totalPages: data.totalPages,
                        totalElements: data.totalElements,
                        currentPage: data.number + 1,
                        warmSearch: true
                    })
                })
            setTimeout(() => this.setState({"show": false}), 3000);
            this.setState(this.initialState);
        } else if(this.state.seasonSearch) {
            const info = {
                page: currentPage,
                size: this.state.animalsPerPage,
                season: this.state.season,
                food: this.state.food,
                seasonSearch: true
            }
            axios.get("http://localhost:8080/animals/needFeed?page=" + info.page +"&size=" + info.size+"&feed=" + info.food+"&season=" + info.season)
                .then(response => response.data)
                .then((data) => {
                    this.setState({
                        animals: data.content,
                        totalPages: data.totalPages,
                        totalElements: data.totalElements,
                        currentPage: data.number + 1
                    })
                })
            setTimeout(() => this.setState({"show": false}), 3000);
            this.setState(this.initialState);
        } else if(this.state.childSearch) {
            const info = {
                page: currentPage,
                size: this.state.animalsPerPage
            }
            axios.get("http://localhost:8080/animals/expectedChild?page=" + info.page +"&size=" + info.size)
                .then(response => response.data)
                .then((data) => {
                    this.setState({
                        animals: data.content,
                        totalPages: data.totalPages,
                        totalElements: data.totalElements,
                        currentPage: data.number + 1,
                        childSearch: true
                    })
                })
            setTimeout(() => this.setState({"show": false}), 3000);
            this.setState(this.initialState);
        }
    };

    cancelAll = () => {
        this.setState({
            "ageSearch" : false,
            "illnessSearch" : false,
            "kindSearch" : false,
            "relocationSearch" : false,
            "warmSearch" : false,
            "childSearch" : false,
            "seasonSearch" : false,
            "vaccineSearch" : false
        });
        this.findAllAnimal(this.state.currentPage);
    };

    changePage = event => {
        let targetPage = parseInt(event.target.value);
        if (isNaN(targetPage)) {
            targetPage = this.state.currentPage;
        }
        if (targetPage > this.state.totalPages) {
            targetPage = this.state.totalPages;
        }
        if(this.state.ageSearch || this.state.illnessSearch || this.state.vaccineSearch || this.state.kindSearch || this.state.relocationSearch || this.state.warmSearch || this.state.seasonSearch || this.state.childSearch){
            this.submitForm(targetPage)
        } else {
            this.findAllAnimal(targetPage);
        }
        this.setState({
            [event.target.name]: targetPage
        });
    };

    firstPage = () => {
        let firstPage = 1;
        if (this.state.currentPage > firstPage) {
            if(this.state.ageSearch || this.state.illnessSearch || this.state.vaccineSearch || this.state.kindSearch || this.state.relocationSearch || this.state.warmSearch || this.state.seasonSearch || this.state.childSearch){
                this.submitForm(firstPage)
            } else {
                this.findAllAnimal(firstPage);
            }
        }
    };

    prevPage = () => {
        let prevPage = 1;
        if (this.state.currentPage > prevPage) {
            if(this.state.ageSearch || this.state.illnessSearch || this.state.vaccineSearch || this.state.kindSearch || this.state.relocationSearch || this.state.warmSearch || this.state.seasonSearch || this.state.childSearch){
                this.submitForm(this.state.currentPage - prevPage)
            } else {
                this.findAllAnimal(this.state.currentPage - prevPage);
            }
        }
    };

    lastPage = () => {
        let condition = Math.ceil(this.state.totalElements / this.state.animalsPerPage);
        if (this.state.currentPage < condition) {
            if(this.state.ageSearch || this.state.illnessSearch || this.state.vaccineSearch || this.state.kindSearch || this.state.relocationSearch || this.state.warmSearch || this.state.seasonSearch || this.state.childSearch){
                this.submitForm(condition)
            } else {
                this.findAllAnimal(condition);
            }
        }
    };

    nextPage = () => {
        if (this.state.currentPage < Math.ceil(this.state.totalElements / this.state.animalsPerPage)) {
            if(this.state.ageSearch || this.state.illnessSearch || this.state.vaccineSearch || this.state.kindSearch || this.state.relocationSearch || this.state.warmSearch || this.state.seasonSearch || this.state.childSearch){
                this.submitForm(this.state.currentPage + 1);
            } else {
                this.findAllAnimal(this.state.currentPage + 1);
            }
        }
    };

    deleteAnimal = (animalId) => {
        axios.delete("http://localhost:8080/animals/deleteAnimalById?id=" + animalId)
            .then(() => {
                this.setState({
                    "show": true,
                    "message": 'Удалено успешно'
                });
                if(this.state.ageSearch || this.state.illnessSearch || this.state.vaccineSearch || this.state.kindSearch || this.state.relocationSearch || this.state.warmSearch || this.state.seasonSearch || this.state.childSearch){
                    this.submitForm(this.state.currentPage)
                } else {
                    this.findAllAnimal(this.state.currentPage);
                }
                setTimeout(() => this.setState({"show": false}), 3000);
            })
    };

    render() {
        const {animals, currentPage, totalPages, age, illness, illnessSearch, ageSearch, vaccine, vaccineSearch, kind, kindSearch, relocationSearch, warmSearch, seasonSearch, season, food, childSearch} = this.state;

        return (
            <div>
                <div style={{"display": this.state.show ? "block" : "none"}} align="right">
                    <MyToast show={ this.state.show} message={ this.state.message}/>
                </div>
                <Card style={{ width: '100rem' }} className="text-center" align="center">
                    <Card.Header>
                        <div style={{"float": "left"}}><h4>
                            Список Животных
                            <Button size="sm" variant="danger" type="button" onClick={this.cancelAll}>Отменить поиск</Button></h4>
                        </div>
                        <Form onSubmit={this.submitFormSeason} id="animalSeasonFormId">
                            <br></br><br></br>
                            <Form.Row>
                                <h5>
                                    Требуется еда в данном сезоне</h5>
                                <Form.Group as={Col} md={4} controlId="formGridFood">
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="food"
                                                  value={food} onChange={this.searchChange}
                                                  placeholder="Введите еду"/>
                                </Form.Group>
                                <Form.Group as={Col} md={4} controlId="formGridSeason">
                                    <Form.Control required as="select"
                                                  custom onChange={this.searchChange}
                                                  name="season" value={season}>
                                        {this.state.seasons.map(season =>
                                            <option key={season.value} value={season.value}>
                                                {season.display}
                                            </option>
                                        )}
                                    </Form.Control>
                                </Form.Group>
                                <Button size="sm" variant="success"
                                        disabled={ageSearch === true || illnessSearch === true || vaccineSearch === true || relocationSearch === true || warmSearch === true || kindSearch === true || childSearch === true}
                                        type="submit">
                                    <FontAwesomeIcon icon={faSave}/> Искать
                                </Button>
                            </Form.Row>
                        </Form>
                        <Form onSubmit={this.submitFormAge} id="animalWarmAgeFormId">
                            <Form.Row>
                                <h5>
                                    Поиск требующих тепла данного возраста</h5>
                                <Form.Group as={Col} md={4} controlId="formGridAge">
                                    <Form.Control required autoComplete="off"
                                                  type="number" name="age"
                                                  min="0"
                                                  value={age} onChange={this.searchChange}
                                                  placeholder="Введите возраст в месяцах"/>
                                </Form.Group>
                                <Button size="sm" variant="success"
                                        disabled={vaccineSearch === true || illnessSearch === true || kindSearch === true || relocationSearch === true || warmSearch === true || seasonSearch === true || childSearch === true}
                                        type="submit">
                                    <FontAwesomeIcon icon={faSave}/> Искать
                                </Button>
                            </Form.Row>
                        </Form>
                        <Form onSubmit={this.submitFormVaccine} id="animalVaccineFormId">
                            <Form.Row>
                                <h5>
                                    Поиск вакцинированных животных</h5>
                                <Form.Group as={Col} md={4} controlId="formGridVaccine">
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="vaccine"
                                                  value={vaccine} onChange={this.searchChange}
                                                  placeholder="Введите название вакцины"/>
                                </Form.Group>
                                <Button size="sm" variant="success"
                                        disabled={ageSearch === true || illnessSearch === true || kindSearch === true || relocationSearch === true || warmSearch === true || seasonSearch === true || childSearch === true}
                                        type="submit">
                                    <FontAwesomeIcon icon={faSave}/> Искать
                                </Button>
                            </Form.Row>
                        </Form>
                        <Form onSubmit={this.submitFormIll} id="animalIllnessFormId">
                            <Form.Row>
                                <h5>
                                    Поиск животных по болезни</h5>
                                <Form.Group as={Col} md={4} controlId="formGridIllness">
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="illness"
                                                  value={illness} onChange={this.searchChange}
                                                  placeholder="Введите болезнь"/>
                                </Form.Group>
                                <Button size="sm" variant="success"
                                        disabled={ageSearch === true || vaccineSearch === true || kindSearch === true || relocationSearch === true || warmSearch === true || seasonSearch === true || childSearch === true}
                                        type="submit">
                                    <FontAwesomeIcon icon={faSave}/> Искать
                                </Button>
                            </Form.Row>
                        </Form>
                        <Form onSubmit={this.submitFormKind} id="animalKindFormId">
                            <Form.Row>
                                <h5>
                                    Поиск совместимых с</h5>
                                <Form.Group as={Col} md={4} controlId="formGridKind">
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="kind"
                                                  value={kind} onChange={this.searchChange}
                                                  placeholder="Введите вид"/>
                                </Form.Group>
                                <Button size="sm" variant="success"
                                        disabled={ageSearch === true || illnessSearch === true || vaccineSearch === true || relocationSearch === true || warmSearch === true || seasonSearch === true || childSearch === true}
                                        type="submit">
                                    <FontAwesomeIcon icon={faSave}/> Искать
                                </Button>
                            </Form.Row>
                        </Form>
                        <Form onSubmit={this.submitFormWarm} id="animalWarmFormId">
                            <Form.Row>
                                <h5>
                                    Нуждаются в отапливаемом помещении </h5>
                                <Button size="sm" variant="success"
                                        disabled={ageSearch === true || illnessSearch === true || vaccineSearch === true || kindSearch === true || relocationSearch === true || seasonSearch === true || childSearch === true}
                                        type="submit">
                                    <FontAwesomeIcon icon={faSave}/> Искать
                                </Button>
                            </Form.Row>
                        </Form>
                        <Form onSubmit={this.submitFormRelocation} id="animalRelocationFormId">
                            <Form.Row>
                                <h5>
                                    Нуждаются в переселении </h5>
                                <Button size="sm" variant="success"
                                        disabled={ageSearch === true || illnessSearch === true || vaccineSearch === true || kindSearch === true || warmSearch === true || seasonSearch === true  || childSearch === true}
                                        type="submit">
                                    <FontAwesomeIcon icon={faSave}/> Искать
                                </Button>
                            </Form.Row>
                        </Form>
                        <Form onSubmit={this.submitFormChild} id="animalChildFormId">
                            <Form.Row>
                                <h5>Фертильны </h5>
                                <Button size="sm" variant="success"
                                        disabled={ageSearch === true || illnessSearch === true || vaccineSearch === true || kindSearch === true || warmSearch === true || seasonSearch === true || relocationSearch === true}
                                        type="submit">
                                    <FontAwesomeIcon icon={faSave}/> Искать
                                </Button>
                            </Form.Row>
                        </Form>
                    </Card.Header>
                    <Card.Body>
                        <Table>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Имя</th>
                                <th>Вид</th>
                                <th>Тип</th>
                                <th>Зона обитания</th>
                                <th>Пол</th>
                                <th>Физ. остояние</th>
                                <th>Потомство</th>
                                <th>Дата рождения</th>
                                <th>Дата департации</th>
                                <th>Причина департации</th>
                                <th>Переселение</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                animals.length === 0 ?
                                    <tr align="center">
                                        <td colSpan="13">Животных нет</td>
                                    </tr>
                                    : animals.map((animal) => (
                                        <tr key={animal.id}>
                                            <td align="center">{animal.id}</td>
                                            <td align="center">{animal.name}</td>
                                            <td align="center">{animal.kindAnimal}</td>
                                            <td align="center">{animal.type}</td>
                                            <td align="center">{animal.climaticHabitat}</td>
                                            <td align="center">{animal.gender}</td>
                                            <td align="center">{animal.physicalState}</td>
                                            <td align="center">{animal.progeny}</td>
                                            <td align="center">{animal.birthday}</td>
                                            <td align="center">{animal.departureDate === null ? <div>-</div>: <div>{animal.departureDate}</div>}</td>
                                            <td align="center">{animal.departureReason === null ? <div>-</div>: <div>{animal.departureReason}</div>}</td>
                                            <td align="center">{animal.needRelocation === true ? <div>Требуется</div>: <div>Не требуется</div>}</td>
                                            <td align="right">
                                            <ButtonGroup>
                                                <Link to={"editAnimal/"+animal.id} className="btn btn-sm btn-outline-primary"><FontAwesomeIcon icon={faEdit} /></Link>{' '}
                                                <Button size="sm" variant="outline-danger"
                                                                     onClick={this.deleteAnimal.bind(this, animal.id)}><FontAwesomeIcon
                                                icon={faTimes}/></Button>
                                            </ButtonGroup>
                                            </td>
                                        </tr>
                                    ))
                            }
                            </tbody>
                        </Table>
                    </Card.Body>
                    {
                        animals.length > 0 ?
                            <Card.Footer>
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
            </div>
        );
    }
}