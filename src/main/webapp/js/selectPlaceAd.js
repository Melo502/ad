document.addEventListener('DOMContentLoaded', function() {
    const websiteSelect = document.getElementById('website');
    const websiteAddress = document.getElementById('websiteAddress');

    // 更新网站地址显示
    websiteSelect.addEventListener('change', function() {
        const selectedValue = this.value;
        websiteAddress.textContent = selectedValue;
    });

    // 全选功能
    const selectAllCheckbox = document.getElementById('selectAll');
    selectAllCheckbox.addEventListener('change', function() {
        const checkboxes = document.querySelectorAll('input[name="adIds"]');
        checkboxes.forEach(checkbox => {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });
});