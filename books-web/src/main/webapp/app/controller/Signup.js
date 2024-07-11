'use strict';

App.controller('Signup', function($scope, $state, $stateParams, Restangular) {
  $scope.user = {}; // Initialize user object
  $scope.errors = {}; // Initialize errors object to store error messages

  // Function to validate username
  // Function to validate username
  $scope.validateUsername = function() {
    if (!$scope.user.username) {
      $scope.errors.username = "Username is required.";
    } else if ($scope.user.username.length < 3) {
      $scope.errors.username = "Username must be at least 3 characters long.";
    } else {
      $scope.errors.username = ""; // Clear error message if username is valid
    }
  };


  // Function to validate email
  $scope.validateEmail = function() {
    if (!$scope.user.email) {
      $scope.errors.email = "Email is required.";
    } else if (!isValidEmail($scope.user.email)) {
      $scope.errors.email = "Invalid email format.";
    } else {
      $scope.errors.email = ""; // Clear error message if email is valid
    }
  };

  // Function to validate password
  $scope.validatePassword = function() {
    if (!$scope.user.password) {
      $scope.errors.password = "Password is required.";
    } else {
      $scope.errors.password = ""; // Clear error message if password is valid
    }

    // Perform individual password checks
    $scope.errors.minPasswordLength = $scope.user.password.length < 8;
    $scope.errors.specialChar = !$scope.containsSpecialChar($scope.user.password);
    $scope.errors.upperCase = !$scope.containsUpperCase($scope.user.password);
    $scope.errors.lowerCase = !$scope.containsLowerCase($scope.user.password);
    $scope.errors.digit = !$scope.containsDigit($scope.user.password);
  };

  // Function to check if password contains special character
  $scope.containsSpecialChar = function(password) {
    return /[!@#$%^&*]/.test(password);
  };

  // Function to check if password contains uppercase letter
  $scope.containsUpperCase = function(password) {
    return /[A-Z]/.test(password);
  };

  // Function to check if password contains lowercase letter
  $scope.containsLowerCase = function(password) {
    return /[a-z]/.test(password);
  };

  // Function to check if password contains digit
  $scope.containsDigit = function(password) {
    return /\d/.test(password);
  };

  // Function to validate confirmation password
  $scope.validateConfirmPassword = function() {
    if (!$scope.user.confirmpassword) {
      $scope.errors.passwordMismatch = "Confirmation password is required.";
    } else if ($scope.user.password !== $scope.user.confirmpassword) {
      $scope.errors.passwordMismatch = "Passwords do not match.";
    } else {
      $scope.errors.passwordMismatch = ""; // Clear error message if confirmation password is valid
    }
  };

  // Function to perform signup
  $scope.signup = function() {
    // Check if any errors exist
    if (Object.keys($scope.errors).some(key => !!$scope.errors[key])) {
      return; // If errors exist, do not proceed with signup
    }

    // If no errors, proceed with signup
    var promise = Restangular.one('user').put($scope.user);
    promise.then(function(response) {
      console.log("Signup Response:", response);
      window.alert("Sign-up Successful!");
      $state.transitionTo('login');
    }).catch(function(error) {
      console.error("Error during signup:", error);
      // Handle error cases here
    });
  };

  // Function to validate email format
  function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
  }
});
