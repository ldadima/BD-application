import React from "react";
import {Button, Card, FormControl, InputGroup, Table} from "react-bootstrap";
import axios from 'axios';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faFastBackward,
    faFastForward,
    faStepBackward,
    faStepForward, faTimes
} from '@fortawesome/free-solid-svg-icons'
import MyToast from "./MyToast";
import {faEdit} from "@fortawesome/free-regular-svg-icons";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import {Link} from "react-router-dom";

export default class IllnessesList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            illnesses: [],
            currentPage: 1,
            illnessesPerPage: 10,
            open: false,
            illnessId: 0,
            show: false,
            message: ''
        };
    }

    componentDidMount() {
        this.findAllIllness(this.state.currentPage);
    }

    findAllIllness(currentPage) {
        currentPage -= 1;
        axios.get("http://localhost:8080/illnesses/showAll?page=" + currentPage + "&size=" + this.state.illnessesPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    illnesses: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1
                });
            });
    }

    changePage = event => {
        let targetPage = parseInt(event.target.value);
        if (isNaN(targetPage)) {
            targetPage = this.state.currentPage;
        }
        if (targetPage > this.state.totalPages) {
            targetPage = this.state.totalPages;
        }
        this.findAllIllness(targetPage);

        this.setState({
            [event.target.name]: targetPage
        });
    };

    firstPage = () => {
        let firstPage = 1;
        if (this.state.currentPage > firstPage) {
            this.findAllIllness(firstPage);
        }
    };

    prevPage = () => {
        let prevPage = 1;
        if (this.state.currentPage > prevPage) {
            this.findAllIllness(this.state.currentPage - prevPage);
        }
    };

    lastPage = () => {
        let condition = Math.ceil(this.state.totalElements / this.state.illnessesPerPage);
        if (this.state.currentPage < condition) {
            this.findAllIllness(condition);
        }
    };

    nextPage = () => {
        if (this.state.currentPage < Math.ceil(this.state.totalElements / this.state.illnessesPerPage)) {
            this.findAllIllness(this.state.currentPage + 1);
        }
    };

    deleteIllness = (illnessId) => {
        axios.delete("http://localhost:8080/illnesses/deleteIllnessById?id=" + illnessId)
            .then(() => {
                this.setState({
                    "show": true,
                    "message": 'Удалено успешно'
                });
                this.findAllIllness(this.state.currentPage);
                setTimeout(() => this.setState({"show": false}), 3000);
            })
    };

    render() {
        const {illnesses, currentPage, totalPages} = this.state;

        return (
            <div>
                <div style={{"display": this.state.show ? "block" : "none"}} align="right">
                    <MyToast show={this.state.show} message={this.state.message}/>
                </div>
                <Card style={{ width: '100rem' }} className="text-center" align="center">
                    <Card.Header>
                        <div style={{"float": "left"}}>
                            <h4>Список Болезней</h4>
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
                                illnesses.length === 0 ?
                                    <tr align="center">
                                        <td colSpan="3">Болезней нет</td>
                                    </tr>
                                    : illnesses.map((illness) => (
                                        <tr key={illness.id}>
                                            <td align="center">{illness.id}</td>
                                            <td align="center">{illness.name}</td>
                                            <td align="right">
                                                <ButtonGroup>
                                                    <Link to={"editIllness/"+illness.id} className="btn btn-sm btn-outline-primary"><FontAwesomeIcon icon={faEdit} /></Link>{' '}
                                                    <Button size="sm" variant="outline-danger"
                                                            onClick={this.deleteIllness.bind(this, illness.id)}><FontAwesomeIcon
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
                        illnesses.length > 0 ?
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