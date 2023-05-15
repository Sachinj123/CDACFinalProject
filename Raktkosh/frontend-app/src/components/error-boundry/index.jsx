import { Component } from "react";

class ErrorBoundry extends Component {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      description: null
    };
  };

  componentDidCatch(error, description) {
    this.setState({error, description});
  }

  render() {
    if (this.state.error) {
      return (
        <h1>Something went wrong.</h1>
      );
    }
    return this.props.children;
  }
};

export default ErrorBoundry;
