import React, {Component} from 'react';
import NavigationBar from "./components/NavigationBar";
import AnimalsList from "./components/AnimalsList";
import Welcome from "./components/Welcome";
import {Col, Container, Row} from 'react-bootstrap';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Animals from "./components/Animals";
import Employees from "./components/Employees";
import EmployeesList from "./components/EmployeesList";
import Zoos from "./components/Zoos";
import ZoosList from "./components/ZoosList";
import FeedsList from "./components/FeedsList";
import Feeds from "./components/Feeds";
import IllnessesList from "./components/IllnessesList";
import Illnesses from "./components/Illnesses";
import Providers from "./components/Providers";
import ProvidersList from "./components/ProvidersList";

export default class App extends Component {
    render() {
        return (
            <Router>
                <NavigationBar/>
                <Container>
                    <Row>
                        <Col lg={12} className={"margin-top"}>
                            <Switch>
                                <Route path="/" exact component={Welcome}/>
                                <Route path="/animalsList" exact component={AnimalsList}/>
                                <Route path="/addAnimal" exact component={Animals}/>
                                <Route path="/editAnimal/:id" exact component={Animals}/>
                                <Route path="/employeesList" exact component={EmployeesList}/>
                                <Route path="/addEmployee" exact component={Employees}/>
                                <Route path="/editEmployee/:id" exact component={Employees}/>
                                <Route path="/zoosList" exact component={ZoosList}/>
                                <Route path="/addZoo" exact component={Zoos}/>
                                <Route path="/editZoo/:id" exact component={Zoos}/>
                                <Route path="/feedsList" exact component={FeedsList}/>
                                <Route path="/addFeed" exact component={Feeds}/>
                                <Route path="/editFeed/:id" exact component={Feeds}/>
                                <Route path="/illnessesList" exact component={IllnessesList}/>
                                <Route path="/addIllness" exact component={Illnesses}/>
                                <Route path="/editIllness/:id" exact component={Illnesses}/>
                                <Route path="/providersList" exact component={ProvidersList}/>
                                <Route path="/addProvider" exact component={Providers}/>
                                <Route path="/editProvider/:id" exact component={Providers}/>
                            </Switch>
                        </Col>
                    </Row>
                </Container>
            </Router>
        );
    }
}
