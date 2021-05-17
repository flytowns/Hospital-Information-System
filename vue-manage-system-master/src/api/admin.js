import request from '../utils/request';


export function  getAllDoctorInfo() {
    return request({
        url: '/admin/get_all_doctor_info',
        method: 'get',
    });
}

export function  readDoctorInfoByCardId(data) {
    return request({
        url: '/admin/read_doctor_info_by_card_id/'+ data.toString(),
        method: 'get',
    });
}

export function  readDoctorInfoByName(data) {
    return request({
        url: '/admin/read_doctor_info_by_name/'+data.toString(),
        method: 'get',
    });
}

export function  updateDoctorInfo(data) {
    return request({
        url: '/admin/update_doctor_info',
        method: 'post',
        data:data
    });
}

export function  deleteDoctorInfo(data) {
    return request({
        url: '/admin/delete_doctor_info_by_card_id/'+data.toString(),
        method: 'delete',
        data:data
    });
}

export function  deletePatientInfo(data) {
    return request({
        url: '/admin/delete_patient_info_by_card_id/'+data.toString(),
        method: 'delete',
        data:data
    });
}

export function  deletePatientDoctorInfo(data) { //data:patient_card_id
    return request({
        url: '/admin/delete_patient_doctor_info_by_patient_card_id/'+data.toString(),
        method: 'delete',
        data:data
    });
}



export function  deleteDoctorInfoList(data) {
    return request({
        url: '/admin/delete_doctor_info_list',
        method: 'delete',
        data:data
    });
}

export function  deletePatientInfoList(data) {
    return request({
        url: '/admin/delete_patient_info_list',
        method: 'delete',
        data:data
    });
}

export function  deletePatientDoctorInfoList(data) {
    return request({
        url: '/admin/delete_patient_doctor_info_list',
        method: 'delete',
        data:data
    });
}

export function  doctorRegister(data) {
    return request({
        url: '/doc/add_doctor_info',
        method: 'post',
        data: data
    });
}

export function  patientDoctorRegister(data) {
    return request({
        url: '/admin/add_patient_doctor_info',
        method: 'post',
        data: data
    });
}

export function  getAllPatientInfo(data) {
    return request({
        url: '/admin/get_all_patient_info',
        method: 'get',
        data: data
    });
}

export function  readPatientInfoByCardId(data) {
    return request({
        url: '/admin/read_patient_info_by_card_id/'+ data.toString(),
        method: 'get',
    });
}

export function  readPatientInfoByName(data) {
    return request({
        url: '/admin/read_patient_info_by_name/'+data.toString(),
        method: 'get',
    });
}

export function  updatePatientInfo(data) {
    return request({
        url: '/admin/update_patient_info',
        method: 'post',
        data:data
    });
}

export function  updatePatientDoctorInfo(data) {
    return request({
        url: '/admin/update_patient_doctor_info',
        method: 'post',
        data:data
    });
}


export function  getAllPateintDoctorInfo() {
    return request({
        url: '/admin/get_all_patient_doctor_complete_info',
        method: 'get',
    });
}

export function  readPatientInfoByDoctorCardId(data) {
    return request({
        url: '/admin/read_patient_info_by_doctor_card_id/'+data.toString(),
        method: 'get',
        data:data
    });
}

export function  readPatientDoctorInfoByPatientCardId(data) {
    return request({
        url: '/admin/read_patient_doctor_info_by_patient_card_id/'+data.toString(),
        method: 'get',
        data:data
    });
}

export function  readPatientDoctorInfoByDoctorCardId(data) {
    return request({
        url: '/admin/read_patient_doctor_info_by_doctor_card_id/'+data.toString(),
        method: 'get',
        data:data
    });
}

export function  download(data) {
    return request({
        url: '/admin/download/'+data.toString(),
        method: 'get',
        data:data
    });
}

export function  deleteSeries(data) {
    return request({
        url: '/admin/delete_series_by_patient_card_id/'+data.toString(),
        method: 'post',
        data:data
    });
}

export function  isSeriesExist(data) {
    return request({
        url: '/admin/is_series_exist/'+data.toString(),
        method: 'get',
        data:data
    });
}

export function  getDoctorAgeData() {
    return request({
        url: '/admin/get_doctor_age_data',
        method: 'get',
    });
}

export function  getPatientAgeData() {
    return request({
        url: '/admin/get_patient_age_data',
        method: 'get',
    });
}

export function  getPatientDiagnoseData() {
    return request({
        url: '/admin/get_patient_diagnose_data',
        method: 'get',
    });
}
