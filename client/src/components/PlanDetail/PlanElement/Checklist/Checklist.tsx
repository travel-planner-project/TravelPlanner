import { useState } from 'react'
import styles from './Checklist.module.scss'
import ListItem from './ListItem'

function Checklist() {
  const [list, setList] = useState<number[]>([1])

  const addList = () => {
    setList(pre => [...pre, pre[pre.length - 1] + 1])
  }

  return (
    <div className={styles.container}>
      <ul className={styles.checklist}>
        {list.map(line => (
          <ListItem key={line} />
        ))}
        <button className={styles.addBtn} type='button' onClick={addList}>
          항목 추가하기
        </button>
      </ul>
    </div>
  )
}

export default Checklist
