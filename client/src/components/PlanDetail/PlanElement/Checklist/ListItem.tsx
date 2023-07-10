import { useState } from 'react'
import Icon from '../../../Common/Icon'
import styles from './ListItem.module.scss'

type LitItem = {
  deleteItem: () => void
  disableMinusBtn: boolean
}

function ListItem({ deleteItem, disableMinusBtn }: LitItem) {
  const [check, setCheck] = useState(false)

  const handleCheckBox = () => {
    setCheck(pre => !pre)
  }

  return (
    <li className={styles.listContainer}>
      <button type='button' onClick={handleCheckBox}>
        {check ? <Icon name='check-box' size={16} /> : <Icon name='uncheck-box' size={16} />}
      </button>
      <input
        type='text'
        placeholder='여기에 입력하세요.'
        className={check ? styles.checked : styles.input}
      />
      <button
        type='button'
        onClick={deleteItem}
        disabled={disableMinusBtn}
        className={disableMinusBtn ? styles.disabled : ''}
      >
        <Icon name='minus-square' size={12} />
      </button>
    </li>
  )
}

export default ListItem
