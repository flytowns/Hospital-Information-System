import request from '../utils/request';

export function  patientRegister(data) {
    return request({
        url: '/pat/add_patient_info',
        method: 'post',
        data: data
    });
}

