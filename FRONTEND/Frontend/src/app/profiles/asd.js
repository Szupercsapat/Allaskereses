$scope.downloadFile = function () {
  $http.get('http://localhost:8080/rft/profile/getCV/asd', {responseType: 'arraybuffer'})
       .success(function (data) {
           var file = new Blob([data], {type: 'application/pdf'});
           var fileURL = URL.createObjectURL(file);
           window.open(fileURL);
    });
};
