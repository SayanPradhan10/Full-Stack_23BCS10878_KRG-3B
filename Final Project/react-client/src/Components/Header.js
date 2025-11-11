import React, { Component } from 'react';
import '../assets/css/header.css';
import '../assets/css/dropdown.css';
import { Link, withRouter } from 'react-router-dom';
import axios from 'axios';
import swal from 'sweetalert2';

class Header extends Component {
  constructor(props) {
    super(props);
    this.state = {
      showDropdown: false,
    };
  }

  handleDropdownToggle = () => {
    this.setState((prev) => ({ showDropdown: !prev.showDropdown }));
  };

  handleMouseLeave = () => {
    // add small delay so menu doesn't close immediately
    setTimeout(() => this.setState({ showDropdown: false }), 200);
  };

  logout = (e) => {
    e.preventDefault();
    const url = 'http://localhost:8080/logout';

    axios
      .post(url, {}, { withCredentials: true })
      .then((res) => {
        localStorage.clear();
        swal.fire({
          icon: 'success',
          title: 'Logout',
          text: 'Successfully logged out',
        }).then(() => {
          this.props.history.push('/login');
          window.location.reload();
        });
      })
      .catch((err) => {
        console.error('Logout failed:', err);
        localStorage.clear();
        this.props.history.push('/login');
      });
  };

  render() {
    return (
      <div>
        <header>
          <nav className="nav dark-nav fixed-top">
            <div className="container">
              <div className="nav-heading">
                <img
                  className="logo"
                  src="https://cdn6.f-cdn.com/build/icons/fl-logo.svg"
                  alt=""
                  height="40"
                  width="170"
                />
              </div>
              <div className="menu" id="open-navbar1">
                <ul className="list">
                  <li>
                    <Link to="/home" className={this.props.home}>
                      Home
                    </Link>
                  </li>
                  <li>
                    <Link to="/dashboard" className={this.props.dashboard}>
                      Dashboard
                    </Link>
                  </li>
                  <li>
                    <Link to="/myprojects" className={this.props.myprojects}>
                      My Projects
                    </Link>
                  </li>
                  <li>
                    <Link to="/postproject">
                      <button type="button" className="btn btn-primary">
                        Post Project
                      </button>
                    </Link>
                  </li>

                  {/* ✅ Profile dropdown fixed */}
                  <li
                    className="dropdown mr-2"
                    onMouseLeave={this.handleMouseLeave}
                  >
                    <a
                      href="/#"
                      className="dropdown-toggle"
                      onClick={this.handleDropdownToggle}
                    >
                      <b className="caret">
                        Hi, {localStorage.getItem('name') || 'User'}
                      </b>
                    </a>

                    <ul
                      className={`dropdown-menu ${
                        this.state.showDropdown ? 'show' : ''
                      }`}
                    >
                      <li>
                        <Link to="/profile">
                          <a>Profile</a>
                        </Link>
                      </li>
                      <li>
                        <a href="/" onClick={this.logout}>
                          Logout
                        </a>
                      </li>
                    </ul>
                  </li>
                </ul>
              </div>
            </div>
          </nav>
        </header>
        <br />
        <br />
        <br />
      </div>
    );
  }
}

export default withRouter(Header);
