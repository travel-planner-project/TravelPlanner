import { useState } from 'react'
import Icon from '../../../Common/Icon'
import styles from './ListItem.module.scss'

function ListItem() {
  const [check, setCheck] = useState(false)

  const handleCheckBox = () => {
    setCheck(pre => !pre)
  }

  return (
    <li className={styles.listContainer}>
      <button type='button' onClick={handleCheckBox}>
        {check ? <Icon name='check-box' size={16} /> : <Icon name='uncheck-box' size={16} />}
      </button>
      <input className={check ? styles.checked : styles.input} />
    </li>
  )
}

export default ListItem
