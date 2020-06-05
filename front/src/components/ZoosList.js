import React from "react";
import {Button, Card, FormControl, InputGroup, Table} from "react-bootstrap";
import axios from 'axios';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faFastBackward,
    faFastForward, faSearch,
    faStepBackward,
    faStepForward, faTimes
} from '@fortawesome/free-solid-svg-icons'
import MyToast from "./MyToast";
import {faEdit} from "@fortawesome/free-regular-svg-icons";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import {Link} from "react-router-dom";

export default class ZoosList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            zoos: [],
            currentPage: 1,
            zoosPerPage: 10,
            open: false,
            zooId: 0,
            show: false,
            message: '',
            search: false
        };
    }

    componentDidMount() {
        this.findAllZoo(this.state.currentPage);
    }

    findAllZoo(currentPage) {
        currentPage -= 1;
        axios.get("http://localhost:8080/zoos/showAll?page=" + currentPage + "&size=" + this.state.zoosPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    zoos: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1,
                    search : false
                });
            });
    }

    cancelSearch = () => {
        this.setState({"search" : false});
        this.findAllZoo(this.state.currentPage);
    };

    searchData = (currentPage) => {
        currentPage -= 1;
        if(isNaN(currentPage)) {
            currentPage = 0;
        }
        if(currentPage > this.state.totalPages) {
            currentPage = this.state.totalPages;
        }
        axios.get("http://localhost:8080/zoos/haveChange?page="+currentPage+"&size="+this.state.zoosPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    zoos: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1,
                    search: true
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
        if (this.state.search) {
            this.searchData(targetPage);
        } else {
            this.findAllZoo(targetPage);
        }
        this.setState({
            [event.target.name]: targetPage
        });
    };

    firstPage = () => {
        let firstPage = 1;
        if (this.state.currentPage > firstPage) {
            if (this.state.search) {
                this.searchData(firstPage);
            } else {
                this.findAllZoo(firstPage);
            }
        }
    };

    prevPage = () => {
        let prevPage = 1;
        if (this.state.currentPage > prevPage) {
            if (this.state.search) {
                this.searchData(this.state.currentPage - prevPage);
            } else {
                this.findAllZoo(this.state.currentPage - prevPage);
            }
        }
    };

    lastPage = () => {
        let condition = Math.ceil(this.state.totalElements / this.state.zoosPerPage);
        if (this.state.currentPage < condition) {
            if (this.state.search) {
                this.searchData(condition);
            } else {
                this.findAllZoo(condition);
            }
        }
    };

    nextPage = () => {
        if (this.state.currentPage < Math.ceil(this.state.totalElements / this.state.zoosPerPage)) {
            if (this.state.search) {
                this.searchData(this.state.currentPage + 1);
            } else {
                this.findAllZoo(this.state.currentPage + 1);
            }
        }
    };

    deleteZoo = (zooId) => {
        axios.delete("http://localhost:8080/zoos/deleteZooById?id=" + zooId)
            .then(() => {
                this.setState({
                    "show": true,
                    "message": 'Удалено успешно'
                });
                if (this.state.search) {
                    this.searchData(this.state.currentPage);
                } else {
                    this.findAllZoo(this.state.currentPage);
                }
                setTimeout(() => this.setState({"show": false}), 3000);
            })
    };

    render() {
        const {zoos, currentPage, totalPages, search} = this.state;

        return (
            <div>
                <div style={{"display": this.state.show ? "block" : "none"}} align="right">
                    <MyToast show={this.state.show} message={this.state.message}/>
                </div>
                <Card style={{ width: '100rem' }} className="text-center" align="center">
                    <Card.Header>
                        <div style={{"float": "left"}}>
                            <h4>Список Зоопарков</h4>
                        </div>
                        <div style={{"float":"right"}}>
                            <InputGroup size="sm">
                                <InputGroup.Append>
                                    <h5>
                                        Производился обмен</h5>
                                    <Button size="sm" disabled={search === true} variant="outline-info" type="button"
                                            onClick={this.searchData}>
                                        <FontAwesomeIcon icon={faSearch}/>
                                    </Button>
                                    <Button size="sm" disabled={search === false} variant="outline-danger" type="button" onClick={this.cancelSearch}>
                                        <FontAwesomeIcon icon={faTimes} />
                                    </Button>
                                </InputGroup.Append>
                            </InputGroup>
                        </div>
                    </Card.Header>
                    <Card.Body>
                        <Table>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Название</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                zoos.length === 0 ?
                                    <tr align="center">
                                        <td colSpan="3">Зоопарков нет</td>
                                    </tr>
                                    : zoos.map((zoo) => (
                                        <tr key={zoo.id}>
                                            <td align="center">{zoo.id}</td>
                                            <td align="center">{zoo.name}</td>
                                            <td align="right">
                                                <ButtonGroup>
                                                    <Link to={"editZoo/"+zoo.id} className="btn btn-sm btn-outline-primary"><FontAwesomeIcon icon={faEdit} /></Link>{' '}
                                                    <Button size="sm" variant="outline-danger"
                                                            onClick={this.deleteZoo.bind(this, zoo.id)}><FontAwesomeIcon
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
                        zoos.length > 0 ?
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