import { useState } from 'react'
import Icon from '../../../Common/Icon'
import styles from './AccountItem.module.scss'

type AccountItemProps = {
  addCost: (cost: number) => void
  deleteItem: (cost: number) => void
  disabledMinusBtn: boolean
}

function AccountItem({ addCost, deleteItem, disabledMinusBtn }: AccountItemProps) {
  const [cost, setCost] = useState(0)

  const costChange = (event: React.FocusEvent<HTMLInputElement>) => {
    addCost(+event.target.value)
    setCost(+event.target.value)
  }

  return (
    <li className={styles.itemContainer}>
      <input placeholder='이용내역' className={styles.inputText} />
      <input placeholder='금액' type='number' onBlur={costChange} className={styles.cost} />
      <span className={styles.won}>원</span>
      <button
        type='button'
        onClick={() => deleteItem(cost)}
        disabled={disabledMinusBtn}
        className={disabledMinusBtn ? styles.disabled : ''}
      >
        <Icon name='minus-square' size={12} />
      </button>
    </li>
  )
}

export default AccountItem
