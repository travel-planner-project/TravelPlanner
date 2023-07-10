import { useState } from 'react'
import styles from './Checklist.module.scss'
import ListItem from './ListItem'

function Checklist() {
  const [list, setList] = useState<number[]>([1])

  const handleListItem = (action: 'plus' | 'minus', line?: number) => {
    if (action === 'plus') {
      setList(pre => [...pre, pre[pre.length - 1] + 1])
    }
    setList(pre => pre.filter(num => num !== line))
  }

  return (
    <div className={styles.container}>
      <ul className={styles.checklist}>
        {list.map(line => (
          <ListItem
            key={line}
            deleteItem={() => handleListItem('minus', line)}
            disableMinusBtn={list.length <= 1}
          />
        ))}
        <button className={styles.addBtn} type='button' onClick={() => handleListItem('plus')}>
          항목 추가하기
        </button>
      </ul>
    </div>
  )
}

export default Checklist
