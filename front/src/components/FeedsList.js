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

export default class FeedsList extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            feeds: [],
            currentPage: 1,
            feedsPerPage: 10,
            open: false,
            feedId: 0,
            show: false,
            message: '',
            search: false
        };
    }

    componentDidMount() {
        this.findAllFeed(this.state.currentPage);
    }

    findAllFeed(currentPage) {
        currentPage -= 1;
        axios.get("http://localhost:8080/feeds/showAll?page=" + currentPage + "&size=" + this.state.feedsPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    feeds: data.content,
                    totalPages: data.totalPages,
                    totalElements: data.totalElements,
                    currentPage: data.number + 1
                });
            });
    }

    cancelSearch = () => {
        this.setState({"search" : false});
        this.findAllFeed(this.state.currentPage);
    };

    searchData = (currentPage) => {
        currentPage -= 1;
        if(isNaN(currentPage)) {
            currentPage = 0;
        }
        if(currentPage > this.state.totalPages) {
            currentPage = this.state.totalPages;
        }
        axios.get("http://localhost:8080/feeds/producedMyself?page="+currentPage+"&size="+this.state.feedsPerPage)
            .then(response => response.data)
            .then((data) => {
                this.setState({
                    feeds: data.content,
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
            this.findAllFeed(targetPage);
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
                this.findAllFeed(firstPage);
            }
        }
    };

    prevPage = () => {
        let prevPage = 1;
        if (this.state.currentPage > prevPage) {
            if (this.state.search) {
                this.searchData(this.state.currentPage - prevPage);
            } else {
                this.findAllFeed(this.state.currentPage - prevPage);
            }
        }
    };

    lastPage = () => {
        let condition = Math.ceil(this.state.totalElements / this.state.feedsPerPage);
        if (this.state.currentPage < condition) {
            if (this.state.search) {
                this.searchData(condition);
            } else {
                this.findAllFeed(condition);
            }
        }
    };

    nextPage = () => {
        if (this.state.currentPage < Math.ceil(this.state.totalElements / this.state.feedsPerPage)) {
            if (this.state.search) {
                this.searchData(this.state.currentPage + 1);
            } else {
                this.findAllFeed(this.state.currentPage + 1);
            }
        }
    };

    deleteFeed = (feedId) => {
        axios.delete("http://localhost:8080/feeds/deleteFeedById?id=" + feedId)
            .then(() => {
                this.setState({
                    "show": true,
                    "message": 'Удалено успешно'
                });
                if (this.state.search) {
                    this.searchData(this.state.currentPage);
                } else {
                    this.findAllFeed(this.state.currentPage);
                }
                setTimeout(() => this.setState({"show": false}), 3000);
            })
    };

    render() {
        const {feeds, currentPage, totalPages, search} = this.state;

        return (
            <div>
                <div style={{"display": this.state.show ? "block" : "none"}} align="right">
                    <MyToast show={this.state.show} message={this.state.message}/>
                </div>
                <Card style={{ width: '100rem' }} className="text-center" align="center">
                    <Card.Header>
                        <div style={{"float": "left"}}>
                            <h4>Список Еды</h4>
                        </div>
                        <div style={{"float":"right"}}>
                            <InputGroup size="sm">
                                <InputGroup.Append>
                                    <h5>
                                        Производится самостоятельно</h5>
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
                                <th>Вид</th>
                                <th>На складе</th>
                                <th>Производится</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                feeds.length === 0 ?
                                    <tr align="center">
                                        <td colSpan="6">Еды нет</td>
                                    </tr>
                                    : feeds.map((feed) => (
                                        <tr key={feed.id}>
                                            <td align="center">{feed.id}</td>
                                            <td align="center">{feed.name}</td>
                                            <td align="center">{feed.type}</td>
                                            <td align="center">{feed.stock}</td>
                                            <td align="center">{feed.volumeIndependentProduction}</td>
                                            <td align="right">
                                                <ButtonGroup>
                                                    <Link to={"editFeed/"+feed.id} className="btn btn-sm btn-outline-primary"><FontAwesomeIcon icon={faEdit} /></Link>{' '}
                                                    <Button size="sm" variant="outline-danger"
                                                            onClick={this.deleteFeed.bind(this, feed.id)}><FontAwesomeIcon
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
                        feeds.length > 0 ?
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