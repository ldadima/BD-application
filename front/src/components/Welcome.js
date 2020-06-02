import React from "react";
import {Jumbotron} from "react-bootstrap";

export default class Welcome extends React.Component {
    render() {
        return (
            <Jumbotron align = "center">
                <h1>Добро пожаловать!</h1>
                <p>
                    Вы зашли на сайт - Информационная система зоопарка!
                    <br></br>Разработчик:
                    Линевич Дмитрий -> d.linevich@g.nsu.ru
                </p>
            </Jumbotron>
        );
    }
}