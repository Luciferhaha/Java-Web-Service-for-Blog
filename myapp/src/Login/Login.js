import React from 'react';
import {Form, Icon, Input, Button, message} from 'antd';
import 'whatwg-fetch';
import './Login.css';
import { Redirect } from 'react-router-dom';
import  {Link} from 'react-router';
const FormItem = Form.Item;
class Login extends React.Component {
    //登录事件
    handleSubmit = (e) => {
        e.preventDefault();

        let url = "http://localhost:8085/test/login";
        fetch(url, {
            method: 'post',
            mode: 'cors',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username:this.props.form.getFieldValue("username"),
                userpwd:this.props.form.getFieldValue("userpwd"),
            })
        }).then(function (response) {
            return response.text();
        }).then(function (body) {
            alert(body);
            if (body=="successful login"){
                // createBrowserHistory().push({userManager});
                // this.setState ({redirect: true});

            }
        });
    }
    render() {
        const {getFieldDecorator} = this.props.form;
        // if (this.state.redirect){
        //     return <Redirect push to="./Admin/userManager" />;
        // }
        return (
            <div class="wrap">
                <div class ="container">
                    <h1>Welcome</h1>
            <Form onSubmit={this.handleSubmit} className="login-form">
                <FormItem>
                    {getFieldDecorator('username', {})(
                        <Input type="account" placeholder="Account"/>
                    )}
                </FormItem>
                <FormItem>
                    {getFieldDecorator('userpwd', {})(
                        <Input type="password"
                            placeholder="Password"/>
                    )}
                </FormItem>
                <FormItem>
                    <Button type="submit" htmlType="submit" className="login-form-button">
                        login
                    </Button>

                </FormItem>
            </Form>
                </div>
                <ul></ul>
            </div>
        );
    }
}
const WrappedNormalLoginForm = Form.create()(Login);
export default WrappedNormalLoginForm;