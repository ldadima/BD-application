import React from "react";
import {Nav, Navbar, NavDropdown} from 'react-bootstrap';
import {Link} from "react-router-dom";

export default class NavigationBar extends React.Component{

    render() {
        return (
            <Navbar bg="light" variant="light">
                <Link to={""} className="navbar-brand">
                    <img src="https://nnst1.gismeteo.ru/images/2020/01/shutterstock_603890771-640x425.jpg" width="40" height="40" alt="brand"/>
                    Информационная система зоопарка
                </Link>
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="mr-auto">
                        <NavDropdown title="Перейти к" id="collasible-nav-dropdown">
                            <NavDropdown.Item href="/animalsList">Список Животных</NavDropdown.Item>
                            <NavDropdown.Item href="/addAnimal">Добавить Животное</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="/employeesList">Список Сотрудники</NavDropdown.Item>
                            <NavDropdown.Item href="/addEmployee">Добавить Сотрудника</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="/zoosList">Список Зоопарков</NavDropdown.Item>
                            <NavDropdown.Item href="/addZoo">Добавить Зоопарк</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="/feedsList">Список Еды</NavDropdown.Item>
                            <NavDropdown.Item href="/addFeed">Добавить Еду</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="/illnessesList">Список Болезней</NavDropdown.Item>
                            <NavDropdown.Item href="/addIllness">Добавить Болезнь</NavDropdown.Item>
                            <NavDropdown.Divider />
                            <NavDropdown.Item href="/providersList">Список Поставщиков</NavDropdown.Item>
                            <NavDropdown.Item href="/addProvider">Добавить Поставщика</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}