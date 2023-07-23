import { useState } from 'react'
import RenderCheckPage from '../../components/Profile/RenderCheck'
import RenderEdit from '../../components/Profile/RenderEdit'
import RenderScuccess from '../../components/Profile/RenderSuccess'

function EditPassword() {
  const [page, setPage] = useState('check')

  // 페이지 상태에 따라 화면 전환
  switch (page) {
    case 'check':
      return <RenderCheckPage />
    case 'edit':
      return <RenderEdit />
    case 'success':
      return <RenderScuccess />
    default:
      return <RenderCheckPage />
  }
}

export default EditPassword
