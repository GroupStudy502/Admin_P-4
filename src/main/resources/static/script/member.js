function changeAuth(el) {
    const formData = new FormData();
    formData.append("memberSeq", el.dataset.member);
    formData.append("authorityName", el.value);
    formData.append("invoke", el.checked);

    const {ajaxLoad} = commonLib;
    ajaxLoad('/authorities/save', 'POST', formData, null, 'json')
        .then(res => {

            console.log(res);
            //alert(res.data);

            if (!res.success) {
                alert(res.message);
            }

        })
        .catch(err => alert(err.message));
}


