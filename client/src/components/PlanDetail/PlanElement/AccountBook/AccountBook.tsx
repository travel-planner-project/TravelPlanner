import { useState } from 'react'
import Icon from '../../../Common/Icon'
import styles from './AccountBook.module.scss'
import AccountItem from './AccountItem'

function AccountBook() {
  const [items, setItems] = useState<number[]>([1])
  const [total, setTotal] = useState<number>(0)

  const handleItems = (type: 'plus' | 'minus', line?: number, cost = 0) => {
    if (type === 'plus') {
      return setItems(pre => [...pre, pre[pre.length - 1] + 1])
    }
    setTotal(pre => pre - cost)
    return setItems(pre => pre.filter(num => num !== line))
  }

  const addCost = (cost: number) => {
    setTotal(pre => pre + cost)
  }

  return (
    <div className={styles.container}>
      <ul className={styles.itemsBox}>
        {items.map(line => (
          <AccountItem
            key={line}
            addCost={addCost}
            deleteItem={(cost: number) => handleItems('minus', line, cost)}
            disabledMinusBtn={items.length <= 1}
          />
        ))}
      </ul>
      <button type='button' onClick={() => handleItems('plus')}>
        <Icon name='plus-square' size={16} />
      </button>
      <div className={styles.line} />
      <pre className={styles.total}>{`총           ${total} 원`}</pre>
    </div>
  )
}

export default AccountBook
