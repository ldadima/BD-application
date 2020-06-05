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
import moment from "moment";
import DatePicker from "react-datepicker";

export default class ProvidersList extends React.Component {

    constructor(props) {
        super(props);
        this.state = this.initialState;
        this.state = {
            providers: [],
            currentPage: 1,
            providersPerPage: 10,
            open: false,
            providerId: 0,
            show: false,
            feedSearch: false,
            dateSearch: false,
            message: '',
            countProviders: 0
        };
        this.searchChange = this.searchChange.bind(this);
        this.submitForm = this.submitForm.bind(this);
    }

    initialState = {
        feed: '',
        begin: new Date(),
        end: new Date()
    };

    componentDidMount() {
        this.findAllProvider(this.state.currentPage);
        this.setCountProviders();
    }

    findAllProvider(currentPage) {
        currentPage -= 1;
        axios.get("http://localhost:8080/providers/showAll?page=" + currentPage + "&size=" + this.state.providersPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    providers: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1
                });
            });
    }

    setCountProviders() {
        axios.get("http://localhost:8080/providers/count")
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    countProviders: data
                });
            });
    }

    searchChange = event => {
        this.setState({
            [event.target.name] : event.target.value
        });
    };

    submitFormFeed = event => {
        event.preventDefault();
        this.setState({feedSearch : true}, () => {
            this.submitForm(1);
        });
    };

    submitFormDate = event => {
        event.preventDefault();
        this.setState({dateSearch : true}, () => {
            this.submitForm(1);
        });
    };

    submitForm(currentPage) {
        currentPage -= 1;
        if (isNaN(currentPage)) {
            currentPage = 0;
        }
        if (currentPage > this.state.totalPages) {
            currentPage = this.state.totalPages;
        }
        if (this.state.feedSearch) {
            const info = {
                page: currentPage,
                size: this.state.providersPerPage,
                feed: this.state.feed
            }
            axios.get("http://localhost:8080/providers/specOfFeed?page=" + info.page + "&size=" + info.size + "&feed=" + info.feed)
                .then(response => response.data)
                .then((data) => {
                    this.setState({
                        providers: data.content,
                        totalPages: data.totalPages,
                        totalElements: data.totalElements,
                        currentPage: data.number + 1,
                        feedSearch: true
                    })
                })
            setTimeout(() => this.setState({"show": false}), 3000);
            this.setState(this.initialState);
        } else if (this.state.dateSearch) {
            const info = {
                page: currentPage,
                size: this.state.providersPerPage,
                begin: moment(this.state.begin).format('YYYY-MM-DD'),
                end: moment(this.state.end).format('YYYY-MM-DD')
            }
            axios.get("http://localhost:8080/providers/supplyForPeriod?page=" + info.page + "&size=" + info.size + "&begin=" + info.begin + "&end=" + info.end)
                .then(response => response.data)
                .then((data) => {
                    this.setState({
                        providers: data.content,
                        totalPages: data.totalPages,
                        totalElements: data.totalElements,
                        currentPage: data.number + 1,
                        dateSearch: true
                    })
                })
            setTimeout(() => this.setState({"show": false}), 3000);
            this.setState(this.initialState);
        }
    }

    cancelAll = () => {
        this.setState({
            "dateSearch" : false,
            "feedSearch" : false
        });
        this.findAllProvider(this.state.currentPage);
    };

    changePage = event => {
        let targetPage = parseInt(event.target.value);
        if (isNaN(targetPage)) {
            targetPage = this.state.currentPage;
        }
        if (targetPage > this.state.totalPages) {
            targetPage = this.state.totalPages;
        }
        if(this.state.dateSearch || this.state.feedSearch){
            this.submitForm(targetPage)
        } else {
            this.findAllProvider(targetPage);
        }

        this.setState({
            [event.target.name]: targetPage
        });
    };

    firstPage = () => {
        let firstPage = 1;
        if (this.state.currentPage > firstPage) {
            if(this.state.dateSearch || this.state.feedSearch){
                this.submitForm(firstPage)
            } else {
                this.findAllProvider(firstPage);
            }
        }
    };

    prevPage = () => {
        let prevPage = 1;
        if (this.state.currentPage > prevPage) {
            if(this.state.dateSearch || this.state.feedSearch){
                this.submitForm(this.state.currentPage - prevPage)
            } else {
                this.findAllProvider(this.state.currentPage - prevPage);
            }
        }
    };

    lastPage = () => {
        let condition = Math.ceil(this.state.totalElements / this.state.providersPerPage);
        if (this.state.currentPage < condition) {
            if(this.state.dateSearch || this.state.feedSearch){
                this.submitForm(condition)
            } else {
                this.findAllProvider(condition);
            }
        }
    };

    nextPage = () => {
        if (this.state.currentPage < Math.ceil(this.state.totalElements / this.state.providersPerPage)) {
            if(this.state.dateSearch || this.state.feedSearch){
                this.submitForm(this.state.currentPage + 1)
            } else {
                this.findAllProvider(this.state.currentPage + 1);
            }
        }
    };

    deleteProvider = (providerId) => {
        axios.delete("http://localhost:8080/providers/deleteProviderById?id=" + providerId)
            .then(() => {
                this.setState({
                    "show": true,
                    "message": 'Удалено успешно'
                });
                if(this.state.dateSearch || this.state.feedSearch){
                    this.submitForm(this.state.currentPage)
                } else {
                    this.findAllProvider(this.state.currentPage);
                }
                setTimeout(() => this.setState({"show": false}), 3000);
            })
    };

    handleChange = date => {
        this.setState({
            "begin": date
        });
    };

    handleEndChange = date => {
        this.setState({
            "end": date
        });
    };

    render() {
        const {providers, currentPage, totalPages, feedSearch, dateSearch, begin, end, feed} = this.state;

        return (
            <div>
                <div style={{"display": this.state.show ? "block" : "none"}} align="right">
                    <MyToast show={this.state.show} message={this.state.message}/>
                </div>
                <Card style={{ width: '100rem' }} className="text-center" align="center">
                    <Card.Header>
                        <div style={{"float": "left"}}>
                            <h4>Список Поставщиков
                            <Button size="sm" variant="danger" type="button" onClick={this.cancelAll}>Отменить поиск</Button></h4>
                        </div>
                        <Form onSubmit={this.submitFormDate} id="providerDateFormId">
                            <br></br><br></br>
                            <Form.Row>
                                <h5>
                                    Заключалась сделка в период</h5>
                                <Form.Group as={Col} md={3} controlId="formGridBegin">
                                    <Form.Label>Дата начала</Form.Label>
                                    <DatePicker
                                        selected={begin}
                                        onChange={this.handleChange}
                                    />
                                </Form.Group>
                                <Form.Group as={Col} md={3} controlId="formGridEnd">
                                    <Form.Label>Дата конца</Form.Label>
                                    <DatePicker
                                        selected={end}
                                        onChange={this.handleEndChange}
                                    />
                                </Form.Group>
                                <Button size="sm" variant="success"
                                        disabled={feedSearch === true}
                                        type="submit">
                                    <FontAwesomeIcon icon={faSave}/> Искать
                                </Button>
                            </Form.Row>
                        </Form>
                        <Form onSubmit={this.submitFormFeed} id="providerFeedFormId">
                            <Form.Row>
                                <h5>
                                    Специальзируется на</h5>
                                <Form.Group as={Col} md={4} controlId="formGridFeed">
                                    <Form.Control required autoComplete="off"
                                                  type="test" name="feed"
                                                  value={feed} onChange={this.searchChange}
                                                  placeholder="Введите продукцию"/>
                                </Form.Group>
                                <Button size="sm" variant="success"
                                        disabled={dateSearch === true}
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
                                <th>Название</th>
                                <th>Начало сотрудничества</th>
                                <th>Окончание сотрудничества</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                providers.length === 0 ?
                                    <tr align="center">
                                        <td colSpan="5">Поставщиков нет</td>
                                    </tr>
                                    : providers.map((provider) => (
                                        <tr key={provider.provId}>
                                            <td align="center">{provider.provId}</td>
                                            <td align="center">{provider.name}</td>
                                            <td align="center">{provider.dateBegin}</td>
                                            <td align="center">{provider.dateEnd}</td>
                                            <td align="right">
                                                <ButtonGroup>
                                                    <Link to={"editProvider/"+provider.provId} className="btn btn-sm btn-outline-primary"><FontAwesomeIcon icon={faEdit} /></Link>{' '}
                                                    <Button size="sm" variant="outline-danger"
                                                            onClick={this.deleteProvider.bind(this, provider.provId)}><FontAwesomeIcon
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
                        providers.length > 0 ?
                            <Card.Footer>
                                <div style={{"float": "left"}}>
                                    Страница {currentPage} из {totalPages}
                                </div>
                                <div style={{"float": "center"}}>
                                    Всего поставщиков: {this.state.countProviders}
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