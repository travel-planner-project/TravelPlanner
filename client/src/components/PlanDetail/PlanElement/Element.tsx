import { useState } from 'react'
import styles from './Element.module.scss'
import Tapbar from './Tapbar'

type ElementViewProps = {
  currentTap: string
  changeTap: React.Dispatch<React.SetStateAction<string>>
}

function ElementView({ currentTap, changeTap }: ElementViewProps) {
  return (
    <div className={styles.container}>
      <Tapbar currentTap={currentTap} changeTap={changeTap} />
      <div className={styles.content}>내용</div>
      <div className={styles.buttons}>
        <div className={styles.okBtn}>확인</div>
        <div className={styles.deleteBtn}>삭제</div>
      </div>
    </div>
  )
}
function Element() {
  const [selectedTap, setSelectedTap] = useState<string>('')
  return <ElementView currentTap={selectedTap} changeTap={setSelectedTap} />
}

export default Element
