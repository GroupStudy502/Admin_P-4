function changeAuth(el) {
    //alert(el.checked);
    //alert(el.dataset.member);
    //alert(el.value);

    const formData = new FormData();
    formData.append("memberSeq", el.dataset.member);
    formData.append("authorityName", el.value);
    formData.append("isTrue", el.checked);

    const {ajaxLoad} = commonLib;
    ajaxLoad('/authorities/save', 'POST', formData)
        .then(res => {
            if (!res.success) {
                alert(res.message);
            }
        })
        .catch(err => alert(err.message));
}


