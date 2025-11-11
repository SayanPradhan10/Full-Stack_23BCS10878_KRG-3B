import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import axios from 'axios';
import { Link } from 'react-router-dom';
import swal from 'sweetalert2'

class ProjectForm extends Component {
  constructor(props) {
    super(props);
    this.state = {
      file: '',
      successMessage: '',
      errorMessage: '',
      title: '',
      description: '',
      skill: '',
      budget: '',
      period: '',
    }
    this._handleChangeFile = this._handleChangeFile.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }
  handleUserInput = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    this.setState({ [name]: value })

  }
  handleSubmit(e) {
    e.preventDefault();

    const { title, description, skill, budget, period, file } = this.state;
    const employerId = localStorage.getItem('id');
    const postProjectAPI = 'http://localhost:8080/postProject';

    if (!title || !description || !skill || !budget || !period) {
      swal.fire({
        icon: 'error',
        title: 'Post Project',
        text: 'Please complete the form.',
      });
      return;
    }

    // ✅ Match backend parameter names exactly
    const formData = new FormData();
    if (file) formData.append('file', file);
    formData.append('employer_id', employerId);
    formData.append('title', title);
    formData.append('description', description);
    formData.append('main_skill_id', skill);
    formData.append('budget_range', budget);
    formData.append('budget_period', period);

    // ✅ Don't set 'Content-Type' manually (browser does it automatically)
    axios
      .post(postProjectAPI, formData, { withCredentials: true })
      .then((res) => {
        if (res.data && res.data.successMsg) {
          swal.fire({
            icon: 'success',
            title: 'Project Posted',
            text: 'Your project was posted successfully!',
          });
          this.setState({
            file: '',
            title: '',
            description: '',
            skill: '',
            budget: '',
            period: '',
          });
        } else {
          swal.fire({
            icon: 'error',
            title: 'Post Failed',
            text: res.data.errorMsg || 'Unknown error',
          });
        }
      })
      .catch((err) => {
        console.error('Error posting project:', err);
        let errorMsg = 'Could not post project. Please try again later.';
        if (err.response && err.response.data && err.response.data.errorMsg) {
          errorMsg = err.response.data.errorMsg;
        }
        swal.fire({
          icon: 'error',
          title: 'Server Error',
          text: errorMsg,
        });
      });

  }


  _handleChangeFile(e) {
    e.preventDefault();
    let reader = new FileReader();
    let file = e.target.files[0];
    // eslint-disable-next-line
    if (file && file.type == 'application/pdf') {
      reader.onloadend = () => {
        this.setState({
          file: file,
        });
      }
      reader.readAsDataURL(file)
    }
    else {
      swal({
        type: 'error',
        title: 'File Upload',
        text: 'Only PDF attachments allowed',
      })
    }
  }
  render() {
    return (
      <div className="container">
        <div class="row">
          <div class="col-md-9 personal-info">
            <h3>Project Info</h3>
            <hr />

            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-lg-3 control-label"><strong>Title</strong></label>
                <div class="col-lg-8">
                  <input class="form-control" type="text" name="title"
                    placeholder="Title" required="" value={this.state.title} onChange={this.handleUserInput} />
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-3 control-label"><strong>Description</strong></label>
                <div class="col-md-8">
                  <textarea class="form-control" rows="5" name="description"
                    placeholder="Description" required="" value={this.state.description} onChange={this.handleUserInput}></textarea>
                </div>
              </div>
              <div class="form-group">
                <label class="col-lg-3 control-label"><strong>Main Skill</strong></label>
                <div class="col-lg-8">
                  <input class="form-control" type="text" name="skill"
                    placeholder="Skill" required="" value={this.state.skill} onChange={this.handleUserInput} />
                </div>
              </div>
              <div class="form-group">
                <label class="col-lg-3 control-label"><strong>Budget Range</strong></label>
                <div class="col-lg-8">
                  <input class="form-control" type="text" name="budget"
                    placeholder="Like 100-200" required="" value={this.state.budget} onChange={this.handleUserInput} />
                </div>
              </div>
              <div class="form-group">
                <label class="col-lg-3 control-label"><strong>Budget Period</strong></label>
                <div class="col-lg-8">
                  <input class="form-control" type="number" name="period"
                    placeholder="Period in Days" required="" value={this.state.period} onChange={this.handleUserInput} />
                </div>
              </div>
              <div class="form-group">
                <label class="col-lg-3 control-label"><strong>Attachment</strong></label>
                <div class="col-lg-8">
                  <input type="file" class="form-control" onChange={this._handleChangeFile} />            </div>
              </div>
              <div class="form-group">
                <label class="col-md-3 control-label"></label>
                <div class="col-md-8">
                  <input type="submit" class="btn btn-primary"
                    value="Post Project" required="" onClick={this.handleSubmit.bind(this)} />
                  <span></span>
                  <Link to='/home'> <input type="reset" class="btn btn-default" value="Cancel" /></Link>
                </div>
              </div>
            </form>
          </div>
        </div>


        <hr />
      
      </div>
    );
  }
}

export default withRouter(ProjectForm);